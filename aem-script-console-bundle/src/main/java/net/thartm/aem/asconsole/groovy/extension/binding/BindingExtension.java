package net.thartm.aem.asconsole.groovy.extension.binding;

import org.apache.sling.api.SlingHttpServletRequest;

import java.util.Arrays;
import java.util.Map;

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
     * @param request The SlingHttpServletRequest
     * @return The Binding
     */
    Map<String, Object> provideVariableMapping(final SlingHttpServletRequest request);

    default void registerObject(final Map<String, Object> bindingMap, final Object object, final String... variableKeys) {
        Arrays.asList(variableKeys).forEach(key -> bindingMap.put(key, object));
    }
}
