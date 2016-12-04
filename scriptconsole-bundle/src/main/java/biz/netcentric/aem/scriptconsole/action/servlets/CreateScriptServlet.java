package biz.netcentric.aem.scriptconsole.action.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.aem.scriptconsole.action.Script;
import biz.netcentric.aem.scriptconsole.groovy.impl.ScriptConsoleConfiguration;
import biz.netcentric.aem.scriptconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.aem.scriptconsole.servlets.WithAccessCheck;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
@SlingServlet(paths = { "/bin/netcentric/scriptconsole/create" }, methods = { "POST", "GET" }, extensions = {
        "json" }, metatype = false)
public class CreateScriptServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(CreateScriptServlet.class);

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
        if (locationResource != null) {
            final String name = request.getParameter("name");
            final String scriptType = request.getParameter("type");

            final Script script = new Script.Builder()
                    .withName(name)
                    .withLocation(locationResource.getPath())
                    .withLanguage(scriptType)
                    .build();

            if (!script.exists(request.getResourceResolver())) {
                script.create(request.getResourceResolver());
            }

            final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            final String json = ow.writeValueAsString(script);
            response.getWriter().append(json);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Unable to create the script at the submitted location. Please check your arguments.");
        }
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return scriptConsoleConfiguration.getAllowedGroups();
    }
}
