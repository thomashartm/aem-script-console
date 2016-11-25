package biz.netcentric.aem.scriptconsole.groovy.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.aem.scriptconsole.ScriptContext;
import biz.netcentric.aem.scriptconsole.ScriptResponse;
import biz.netcentric.aem.scriptconsole.ScriptService;
import biz.netcentric.aem.scriptconsole.empty.EmptyScriptResponse;
import biz.netcentric.aem.scriptconsole.groovy.GroovyPersistableScript;
import biz.netcentric.aem.scriptconsole.groovy.GroovyScriptContext;
import biz.netcentric.aem.scriptconsole.groovy.impl.ScriptConsoleConfiguration;
import biz.netcentric.aem.scriptconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.aem.scriptconsole.servlets.RequestDefaults;
import biz.netcentric.aem.scriptconsole.servlets.WithAccessCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Shell runner servlet that executes the content property of an incoming JSON post request. <br />
 * Any shell execution is bound the user's actual authorization level.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@SlingServlet(paths = { "/bin/netcentric/scriptconsole/run/groovy" }, methods = { "POST", "GET" }, extensions = {
        "json" }, metatype = false)
public class GroovyScriptRunnerServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyScriptRunnerServlet.class);

    @Reference
    private ScriptService scriptService;

    @Reference
    private ScriptConsoleConfiguration scriptConsoleConfiguration;

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        // guard clause that checks wether the user has all required priviledges to access the service
        if (!isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this service.");
        }

        final ScriptResponse scriptResponse = processScript(request);
        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String json = ow.writeValueAsString(scriptResponse);

        response.getWriter().append(json);
    }

    private ScriptResponse processScript(final SlingHttpServletRequest request) throws RepositoryException, IOException {
        final Map<String, String[]> map = request.getParameterMap();
        if (map.containsKey(RequestDefaults.PARAMETER_SCRIPT)) {
            return processSubmittedScript(request);
        } else if (map.containsKey(RequestDefaults.PARAMETER_SCRIPT_LOCATION)) {
            return processStoredScript(request);
        }

        return new EmptyScriptResponse();
    }

    private ScriptResponse processSubmittedScript(final SlingHttpServletRequest request) {
        final String script = request.getParameter(RequestDefaults.PARAMETER_SCRIPT);
        final ScriptContext context = new GroovyScriptContext(request);

        final GroovyPersistableScript groovyScript = new GroovyPersistableScript(script);
        return scriptService.runScript(groovyScript, context);
    }

    private ScriptResponse processStoredScript(final SlingHttpServletRequest request) throws RepositoryException, IOException {
        final String path = request.getParameter(RequestDefaults.PARAMETER_SCRIPT_LOCATION);
        final ScriptContext context = new GroovyScriptContext(request);

        final Resource resource = request.getResourceResolver().getResource(path);

        if (resource != null) {
            final Resource contentResource = resource.getChild(JcrConstants.JCR_CONTENT);
            if (contentResource != null) {
                InputStream is = null;
                BufferedInputStream bin = null;
                try {
                    is = contentResource.adaptTo(InputStream.class);
                    bin = new BufferedInputStream(is);

                    final String scriptSource = IOUtils.toString(bin);

                    if (LOG.isTraceEnabled()) {
                        LOG.trace(scriptSource);
                    }

                    final GroovyPersistableScript groovyScript = new GroovyPersistableScript(scriptSource);
                    return scriptService.runScript(groovyScript, context);
                } finally {
                    close(bin, is);
                }

            }
        }

        throw new RepositoryException(String.format("Jcr data element for path [%s] is empty", path));
    }

    private void close(final InputStream... streams) throws IOException {
        for (final InputStream stream : streams) {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return scriptConsoleConfiguration.getAllowedGroups();
    }
}
