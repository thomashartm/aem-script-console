package biz.netcentric.nclabs.groovyconsole.form;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

/**
 * Creates and manages groovy backing forms.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public interface FormService {

    void createFormFields(final ResourceResolver resourceResolver, final String location, final List<FormField> formFields)
            throws PersistenceException;
}
