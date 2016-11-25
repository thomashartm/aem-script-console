package biz.netcentric.aem.scriptconsole;

import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptContext {

    SlingHttpServletRequest getCurrentRequest();

    Map<String, Object> getContextParameters();

    ResourceResolver getResourceResolver();
}
