package biz.netcentric.aem.scriptconsole.groovy.extension.binding;

import java.util.Arrays;
import java.util.Map;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;

/**
 * Registers the variable bindings.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
public interface BindingExtension {

    /**
     * Provides a Binding based on the request.
     * 
     * @param BindingCommons The BindingCommons
     * @return The Binding
     */
    Map<String, Object> provideVariableMapping(final BindingCommons bindingCommons);

    /**
     * Register the object with the binding map under the deinfed keys e.g. register a resourceResolver object with the keys resolver,
     * resourceResolver and rr
     * 
     * @param bindingMap Map containing bindings
     * @param object Object to egister
     * @param variableKeys String arrays of keys
     */
    default void registerObject(final Map<String, Object> bindingMap, final Object object, final String... variableKeys) {
        Arrays.asList(variableKeys).forEach(key -> bindingMap.put(key, object));
    }
}
