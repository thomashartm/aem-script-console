package biz.netcentric.nclabs.groovyconsole.form.impl;

import biz.netcentric.nclabs.groovyconsole.form.FormField;
import biz.netcentric.nclabs.groovyconsole.form.FormService;
import biz.netcentric.nclabs.groovyconsole.servlets.WithRequestParameters;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a form based for a groovy script container.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
@SlingServlet(paths = { "/bin/nclabs/groovyconsole/form" }, methods = { "POST" }, extensions = { "html" }, metatype = false)
public class FormBuilderServlet extends SlingAllMethodsServlet implements WithRequestParameters {

    private static final Logger LOG = LoggerFactory.getLogger(FormBuilderServlet.class);

    @Reference
    private FormService formService;

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException,
            ServletException {

        final String[] fieldNames = request.getParameterValues(FormParam.NAME.getName());
        final String[] fieldTypes = request.getParameterValues(FormParam.TYPE.getName());
        final String path = request.getParameter(FormParam.PATH.getName());

        final List<FormField> formFields = new ArrayList<>();
        for (int i = 0; i < fieldNames.length; i++) {

            final FormField formField = new FormField(fieldNames[i], fieldTypes[i]);

            /*final String[] mandatoryValues = parameters.get(FormParam.MANDATORY);
            if (mandatoryValues != null && mandatoryValues.length > 0) {
                formField.setMandatory(Boolean.getBoolean(mandatoryValues[i]));
            } else {
                formField.setMandatory(false);
            }*/

            formFields.add(formField);
        }

        this.formService.createFormFields(request.getResourceResolver(), path, formFields);
    }

    private String getRequestBody(final SlingHttpServletRequest request) throws IOException {
        final InputStream inputStream = request.getInputStream();
        final String charset = StringUtils.isBlank(request.getCharacterEncoding()) ? "UTF-8" : request.getCharacterEncoding();

        return IOUtils.toString(inputStream, charset);
    }
}
