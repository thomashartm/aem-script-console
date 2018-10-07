package biz.netcentric.nclabs.groovyconsole.groovy.extension.closure;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import groovy.lang.Closure;

/**
 * Closure bound to a methodName.
 * Bindable closures are getting executed whenever the method is directly used from within a script.
 * They bind th emethod to a certain functionality implemented in the implementing closure.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class BindableClosure extends Closure {

    private BindingCommons bindingCommons;

    private String methodName;

    /**
     * Constructor
     *
     * @param methodName      name to bind to
     * @param bindingsCommons commons for the binding
     * @param owner           The owner
     * @param thisObject      the binding object
     */
    public BindableClosure(final String methodName, final BindingCommons bindingsCommons, final Object owner, final Object thisObject) {
        super(owner, thisObject);
        this.bindingCommons = bindingsCommons;
        this.methodName = methodName;
    }

    /**
     * Constructor
     *
     * @param methodName      name to bind to
     * @param bindingsCommons commons for the binding
     * @param owner           The owner
     */
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
