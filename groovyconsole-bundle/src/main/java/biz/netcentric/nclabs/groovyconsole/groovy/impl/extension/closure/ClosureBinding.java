package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure;

import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.BindingCommons;

import java.util.Collection;

/**
 * Closure binding which can be directly implemented by the closure or a service that create the closure. Will be collected by a registry
 * and aggregated to a single binding.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public interface ClosureBinding {

    /**
     * Provides a a groovy closure
     * 
     * @return Closure to execute
     */
    Collection<BindableClosure> getClosures(final BindingCommons bindingCommons);
}
