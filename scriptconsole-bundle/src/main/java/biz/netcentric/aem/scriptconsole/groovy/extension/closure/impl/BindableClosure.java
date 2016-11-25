package biz.netcentric.aem.scriptconsole.groovy.extension.closure.impl;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;
import groovy.lang.Closure;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class BindableClosure extends Closure {

    private BindingCommons bindingCommons;

    private String methodName;

    public BindableClosure(final String methodName, final BindingCommons bindingsCommons, final Object owner, final Object thisObject) {
        super(owner, thisObject);
        this.bindingCommons = bindingsCommons;
        this.methodName = methodName;
    }

    public BindableClosure(final String methodName, final BindingCommons bindingsCommons, final Object owner) {
        super(owner);
        this.bindingCommons = bindingsCommons;
        this.methodName = methodName;
    }

    public BindingCommons getBindingCommons() {
        return bindingCommons;
    }

    /**
     * Provides a variable name required to bind the closure to the shell
     *
     * @return String name
     */
    public String getBindingVariableName() {
        return this.methodName;
    }
}
