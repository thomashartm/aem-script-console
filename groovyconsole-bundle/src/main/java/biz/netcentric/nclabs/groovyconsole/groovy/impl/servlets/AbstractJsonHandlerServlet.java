package biz.netcentric.nclabs.groovyconsole.groovy.impl.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Takes care of rendering JSON responses for incoming requests via an object mapper.
 * Any backend servlet which returns JSOn must use this base class as it takes care of the actual rendering and the default headers.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public abstract class AbstractJsonHandlerServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJsonHandlerServlet.class);

    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    protected final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {
        processIncomingRequest(request, response);
    }

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {
        processIncomingRequest(request, response);
    }

    private void processIncomingRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            handleRequest(request, response, mapper);

            setDefaultResponseHeaders(request, response);
            setCustomResponseHeaders(request, response);

        } catch (Exception e) {
            LOG.error("Exception while ..." + e, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request.");
        }
    }

    private String getCharSet(final SlingHttpServletRequest request) {
        final String requestCharset = request.getCharacterEncoding();
        return StringUtils.isBlank(requestCharset) ? CharEncoding.UTF_8 : requestCharset;
    }

    protected void setDefaultResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(getCharSet(request));
    }

    public abstract void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response);

    public abstract void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper)
            throws IOException, RepositoryException;

    public void setStatusCode(final SlingHttpServletResponse response, final int statusCode) {
        response.setStatus(statusCode);
    }
}
