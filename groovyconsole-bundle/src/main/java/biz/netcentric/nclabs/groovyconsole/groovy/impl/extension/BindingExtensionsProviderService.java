package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension;

import biz.netcentric.nclabs.groovyconsole.ScriptExecutionContext;
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
     * @param context      The context
     * @return The Binding
     */
    Binding createBindings(final ScriptExecutionContext context);
}
