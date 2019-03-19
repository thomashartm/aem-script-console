package biz.netcentric.nclabs.groovyconsole.groovy.impl.servlets;

import biz.netcentric.nclabs.groovyconsole.ScriptService;
import biz.netcentric.nclabs.groovyconsole.empty.EmptyScriptResponse;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.DynamicGroovyScript;
import biz.netcentric.nclabs.groovyconsole.servlets.WithAccessCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Endpoint for reading and writing scripts to the repository.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
@Component(
        service = { Servlet.class },
        property = {
                "sling.servlet.paths=/bin/nclabs/groovyconsole/save",
                "sling.servlet.methods=POST",
                "sling.servlet.extensions=json"
        }
)
public class PersistanceServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    private static final String DEFAULT_SCRIPT_LOCATION = "/etc/nclabs/groovyconsole/scripts";

    private static final String UNAUTHORIZED_ERROR_MESSAGE = "This user is not authorized to work with the script.";

    @Reference
    private ScriptService scriptService;

    @Override
    public void setCustomResponseHeaders(SlingHttpServletRequest request, SlingHttpServletResponse response) {

    }

    @Override
    public void handleRequest(SlingHttpServletRequest request, SlingHttpServletResponse response,
            ObjectMapper mapper) throws IOException, RepositoryException {
        this.checkForPermissions(request, response);

        final String parent = request.getParameter("path");

        final String source = request.getParameter("script");

        final String name = request.getParameter("name");

        final String fullStorageLocation = request.getParameter("fullLocation");

        final ResourceResolver resourceResolver = request.getResourceResolver();
        Resource scriptContainer = resourceResolver.getResource(parent);
        if (scriptContainer == null) {
            scriptContainer = resourceResolver.getResource(DEFAULT_SCRIPT_LOCATION);
        }

        final DynamicGroovyScript groovyScript = new DynamicGroovyScript(source, ".groovy");
        groovyScript.save(scriptContainer, name);

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String json = ow.writeValueAsString(new EmptyScriptResponse());

        response.getWriter().append(json);
    }

    private void save(final Resource parent, final String source, final String name) {

    }

    private void checkForPermissions(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws RepositoryException, IOException {
        // guard clause that checks wether the user has all required priviledges to access the service
        if (!this.isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_ERROR_MESSAGE);
        }
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return Collections.EMPTY_LIST;
    }
}
