package net.thartm.aem.asconsole.script.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class ScriptPersistenceServlet extends AbstractJsonPostHandlerServlet {

    private static final String SCRIPT = "script";
    private static final String SCRIPT_TYPE = "scriptType";
    private static final String SCRIPT_NAME = "scriptName";

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        final String script = request.getParameter(SCRIPT);
        final String scriptType = request.getParameter(SCRIPT_TYPE);
        final String scriptName = request.getParameter(SCRIPT_NAME);
    }
}
