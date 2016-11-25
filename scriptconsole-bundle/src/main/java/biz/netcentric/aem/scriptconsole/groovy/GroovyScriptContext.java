package biz.netcentric.aem.scriptconsole.groovy;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import biz.netcentric.aem.scriptconsole.ScriptContext;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptContext implements ScriptContext {

    private final SlingHttpServletRequest request;

    public GroovyScriptContext(final SlingHttpServletRequest request) {
        this.request = request;
    }

    @Override
    public SlingHttpServletRequest getCurrentRequest() {
        return this.request;
    }

    @Override
    public Map<String, Object> getContextParameters() {
        return new HashMap<>();
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return this.request.getResourceResolver();
    }
}
