package biz.netcentric.nclabs.groovyconsole.form.impl;

import biz.netcentric.nclabs.groovyconsole.form.FormField;
import biz.netcentric.nclabs.groovyconsole.form.FormService;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(metatype = false)
@Service
public class FormServiceImpl implements FormService {

    private static final String FORM_PARENT = "form";

    public void createFormFields(final ResourceResolver resourceResolver, final String location, final List<FormField> formFields)
            throws PersistenceException {
        final Resource jcrContent = retrieveScriptContent(resourceResolver, location);
        if (jcrContent != null) {
            removeExistingForm(resourceResolver, jcrContent);
            createForm(resourceResolver, formFields, jcrContent);
        }
    }

    private Resource retrieveScriptContent(final ResourceResolver resourceResolver, final String location) {
        final String path = String.format("%s/%s", location, JcrConstants.JCR_CONTENT);
        return resourceResolver.getResource(path);
    }

    private void createForm(final ResourceResolver resourceResolver, final List<FormField> formFields, final Resource jcrContent)
            throws PersistenceException {
        final Map<String, Object> parentProperties = createProperties();
        final Resource formParent = resourceResolver.create(jcrContent, FORM_PARENT, parentProperties);

        if (formParent != null) {
            for (final FormField field : formFields) {
                final Map<String, Object> fieldProperties = createProperties();
                fieldProperties.put("sling:resourceType", field.getType());
                resourceResolver.create(formParent, field.getName(), fieldProperties);
            }

            if (resourceResolver.hasChanges()) {
                resourceResolver.commit();
            }
        }
    }

    private void removeExistingForm(final ResourceResolver resourceResolver, final Resource jcrContent) throws PersistenceException {
        final Resource formParent = jcrContent.getChild(FORM_PARENT);
        if (formParent != null) {
            resourceResolver.delete(formParent);
            if (resourceResolver.hasChanges()) {
                resourceResolver.commit();
            }
        }
    }

    private Map<String, Object> createProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        return properties;
    }
}
