package biz.netcentric.nclabs.groovyconsole.groovy.impl.servlets;

import biz.netcentric.nclabs.groovyconsole.ScriptService;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.PersistedGroovyScript;
import biz.netcentric.nclabs.groovyconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.nclabs.groovyconsole.servlets.WithAccessCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
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
public class GroovyScriptPersistanceServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

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

        final String path = request.getParameter("path");

        final ResourceResolver resourceResolver = request.getResourceResolver();
        final Resource scriptContainer = resourceResolver.getResource(path);

        final PersistedGroovyScript persistedGroovyScript = new PersistedGroovyScript(scriptContainer);
        if(persistedGroovyScript != null){
            // Save it
            persistedGroovyScript.save(resourceResolver, path, StringUtils.EMPTY);
        }
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
