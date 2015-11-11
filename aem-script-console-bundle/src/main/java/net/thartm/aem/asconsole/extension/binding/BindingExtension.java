package net.thartm.aem.asconsole.extension.binding;

import groovy.lang.Binding;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.Map;
import java.util.stream.Stream;

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
        Stream.of(variableKeys).map(key -> bindingMap.put(key, object));
    }
}
