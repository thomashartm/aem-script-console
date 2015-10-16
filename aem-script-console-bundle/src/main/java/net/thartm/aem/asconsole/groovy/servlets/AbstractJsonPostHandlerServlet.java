package net.thartm.aem.asconsole.groovy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.json.JsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public abstract class AbstractJsonPostHandlerServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJsonPostHandlerServlet.class);

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ENCODING_UTF8 = "UTF-8";

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            handleRequest(request, response, mapper);
            setResponseHeaders(response);
        } catch (Exception e) {
            LOG.error("Exception while ..." + e, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request.");
        }
    }

    public abstract void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper)
            throws IOException, RepositoryException;

    private String getCharSet(final SlingHttpServletRequest request) {
        final String requestCharset = request.getCharacterEncoding();
        return StringUtils.isBlank(requestCharset) ? ENCODING_UTF8 : requestCharset;
    }

    protected void setResponseHeaders(final SlingHttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(ENCODING_UTF8);
    }
}
