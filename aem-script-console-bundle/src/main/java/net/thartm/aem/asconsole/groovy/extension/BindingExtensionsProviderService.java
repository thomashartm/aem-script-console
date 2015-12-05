package net.thartm.aem.asconsole.groovy.extension;

import groovy.lang.Binding;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Provides access to {@link net.thartm.aem.asconsole.groovy.extension.binding.BindingExtension}s
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
public interface BindingExtensionsProviderService {

    /**
     * Get's the aggregated binding for console specific extensions.
     * 
     * @param request The SlingHttpServletRequest
     * @return The Binding
     */
    Binding getExtensionsBinding(final SlingHttpServletRequest request);
}
