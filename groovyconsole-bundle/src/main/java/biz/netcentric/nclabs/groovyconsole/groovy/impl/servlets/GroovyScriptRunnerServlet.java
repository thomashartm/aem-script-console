package biz.netcentric.nclabs.groovyconsole.groovy.impl.servlets;

import biz.netcentric.nclabs.groovyconsole.ScriptExecutionContext;
import biz.netcentric.nclabs.groovyconsole.ScriptResponse;
import biz.netcentric.nclabs.groovyconsole.ScriptService;
import biz.netcentric.nclabs.groovyconsole.empty.EmptyScriptResponse;
import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScriptExecutionContext;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.DynamicGroovyScript;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.PersistedGroovyScript;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.ScriptConsoleConfiguration;
import biz.netcentric.nclabs.groovyconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.nclabs.groovyconsole.servlets.DefaultParameter;
import biz.netcentric.nclabs.groovyconsole.servlets.WithAccessCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Shell runner servlet that executes the content property of an incoming JSON post request. <br />
 * Any shell execution is bound the user's actual authorization level.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@SlingServlet(paths = { "/bin/nclabs/groovyconsole/execute" }, methods = { "POST", "GET" }, extensions = { "json" }, metatype = false)
public class GroovyScriptRunnerServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private static final String UNAUTHORIZED_ERROR_MESSAGE = "This user is not authorized to execute the script.";

    private static final String SUBSERVICE = "groovy-script-subservice";

    private static final Logger LOG = LoggerFactory.getLogger(GroovyScriptRunnerServlet.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private ScriptService scriptService;

    @Reference
    private ScriptConsoleConfiguration scriptConsoleConfiguration;

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        this.checkForPermissions(request, response);
        final ScriptResponse scriptResponse = this.processScript(request, response);

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String json = ow.writeValueAsString(scriptResponse);

        response.getWriter().append(json);
    }

    private ScriptResponse processScript(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws RepositoryException, IOException {
        final Map<String, String[]> map = request.getParameterMap();
        if (map.containsKey(DefaultParameter.SCRIPT_LOCATION.get())) {
            return this.processStoredScript(request, response);
        } else if(map.containsKey(DefaultParameter.SCRIPT.get())){
            return this.processScriptSubmission(request, response);
        }

        return new EmptyScriptResponse();
    }


    private void checkForPermissions(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws RepositoryException, IOException {
        // guard clause that checks wether the user has all required priviledges to access the service
        if (!this.isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_ERROR_MESSAGE);
        }
    }

    private ScriptResponse processScriptSubmission(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
        final String source = request.getParameter(DefaultParameter.SCRIPT.get());

        if(StringUtils.isNotEmpty(source)){
            final ScriptExecutionContext context = new GroovyScriptExecutionContext(request);

            final DynamicGroovyScript groovyScript = new DynamicGroovyScript(source, ".groovy");
            return scriptService.runScript(groovyScript, context);
        }
        return new EmptyScriptResponse();
    }

    private ScriptResponse processStoredScript(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws RepositoryException, IOException {
        final String path = request.getParameter(DefaultParameter.SCRIPT_LOCATION.get());
        final Resource resource = request.getResourceResolver().getResource(path);

        if (resource != null) {
            ResourceResolver serviceResolver = null;
            try {

                final PersistedGroovyScript groovyScript = new PersistedGroovyScript(resource);

                ScriptExecutionContext context = new GroovyScriptExecutionContext(request);
                if (StringUtils.isNotEmpty(groovyScript.getUserName())) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Using service user parameter [{}] to execute script", groovyScript.getUserName());
                    }
                    serviceResolver = getResourceResolverForCustomUser(groovyScript.getUserName());
                    context = getScriptContextForCustomUser(request, serviceResolver);
                }

                return this.scriptService.runScript(groovyScript, context);
            } catch (LoginException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_ERROR_MESSAGE);
            } finally {
                if (serviceResolver != null) {
                    serviceResolver.close();
                }
            }
        }

        throw new RepositoryException(String.format("Jcr data element for path [%s] is empty", path));
    }

    private ResourceResolver getResourceResolverForCustomUser(final String userParameter) throws LoginException {
        final Map<String, Object> authenticationInfo = new HashMap<>();
        authenticationInfo.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE);
        authenticationInfo.put(ResourceResolverFactory.USER, userParameter);
        return this.resourceResolverFactory.getServiceResourceResolver(authenticationInfo);
    }

    private ScriptExecutionContext getScriptContextForCustomUser(final SlingHttpServletRequest request,
            final ResourceResolver resourceResolver) throws LoginException {
        final Set<String> usersWhitelist = this.scriptConsoleConfiguration.getAllowedUsers();

        if (!usersWhitelist.contains(resourceResolver.getUserID())) {
            throw new LoginException("Unable to use non existing service user to execute groovy script");
        }

        return new GroovyScriptExecutionContext(request, resourceResolver);
    }

    @Override
    public void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return this.scriptConsoleConfiguration.getAllowedGroups();
    }
}
