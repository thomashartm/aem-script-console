package net.thartm.aem.asconsole.script.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.thartm.aem.asconsole.util.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2016
 */
@SlingServlet(paths = { "/apps/aemscriptconsole/content/formeditor" }, selectors = { "saveform" }, methods = { "POST" }, metatype = true)
public class SaveFormFieldsServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJsonPostHandlerServlet.class);

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ENCODING_UTF8 = "UTF-8";

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {
        try {
            final String path = PathUtil.pathFromSuffix(request);
            // suffix containig path is mandatory for any POST requests
            if (StringUtils.isEmpty(path)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing a correct path suffix.");
                return;
            }

            final ResourceResolver resourceResolver = request.getResourceResolver();
            final Resource resource = resourceResolver.getResource(path);
            if(resource == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Path suffix does not point to any resource.");
                return;
            }


            // TODO save form below items node
            setResponseHeaders(response);
        } catch (Exception e) {
            LOG.error("Exception while ..." + e, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request.");
        }
    }

    private void saveFormData(){

    }

    protected void setResponseHeaders(final SlingHttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(ENCODING_UTF8);
    }
}
