package biz.netcentric.aem.scriptconsole.groovy.extension;

import org.apache.sling.api.SlingHttpServletRequest;

import groovy.lang.Binding;

/**
 * Provides access to object and variable {@link Binding}s
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
    Binding createBindings(final SlingHttpServletRequest request);
}
