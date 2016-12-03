package biz.netcentric.aem.scriptconsole.action.servlets;

import biz.netcentric.aem.scriptconsole.groovy.impl.ScriptConsoleConfiguration;
import biz.netcentric.aem.scriptconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.aem.scriptconsole.servlets.WithAccessCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
@SlingServlet(paths = { "/bin/netcentric/scriptconsole/create" }, methods = { "POST", "GET" }, extensions = {
        "json" }, metatype = false)
public class CreateScriptServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    @Reference
    private ScriptConsoleConfiguration scriptConsoleConfiguration;

    @Override
    public void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
    }

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response, final ObjectMapper mapper)
            throws IOException, RepositoryException {
        // guard clause that checks wether the user has all required priviledges to access the service
        if (!isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this service.");
        }

        final Resource locationResource = request.getRequestPathInfo().getSuffixResource();
        if(locationResource != null) {
            final RequestParameter scriptName = request.getRequestParameter("name");
            final RequestParameter scriptType = request.getRequestParameter("type");
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to create the script at the submitted location. Please check your arguments.");
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return scriptConsoleConfiguration.getAllowedGroups();
    }
}
