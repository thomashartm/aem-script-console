package net.thartm.aem.asconsole.script.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2016
 */
@SlingServlet(paths = { "/bin/private/acfs/script/save" }, methods = { "POST" }, extensions = { "json" }, metatype = true)
public class SaveFormFieldsServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJsonPostHandlerServlet.class);

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ENCODING_UTF8 = "UTF-8";

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {
        try {


            setResponseHeaders(response);
        } catch (Exception e) {
            LOG.error("Exception while ..." + e, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request.");
        }
    }

    protected void setResponseHeaders(final SlingHttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(ENCODING_UTF8);
    }
}
