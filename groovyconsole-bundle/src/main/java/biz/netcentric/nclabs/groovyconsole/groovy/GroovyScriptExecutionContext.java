package biz.netcentric.nclabs.groovyconsole.groovy;

import biz.netcentric.nclabs.groovyconsole.ScriptExecutionContext;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptExecutionContext implements ScriptExecutionContext {

    private final SlingHttpServletRequest request;

    private ResourceResolver resolver;

    public GroovyScriptExecutionContext(final SlingHttpServletRequest request) {
        this.request = request;
    }

    public GroovyScriptExecutionContext(final SlingHttpServletRequest request, final ResourceResolver resolver) {
        this.request = request;
        this.resolver = resolver;
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
        return this.resolver;
    }
}
