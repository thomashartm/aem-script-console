package net.thartm.aem.asconsole.script;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Map;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptContext {

    SlingHttpServletRequest getCurrentRequest();

    Map<String, Object> getContextParameters();

    ResourceResolver getResourceResolver();
}
