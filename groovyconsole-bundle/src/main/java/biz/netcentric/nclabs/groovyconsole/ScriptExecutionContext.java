package biz.netcentric.nclabs.groovyconsole;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Map;

/**
 * Script execution context information
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptExecutionContext {

    /**
     * The current request
     *
     * @return
     */
    SlingHttpServletRequest getCurrentRequest();

    /**
     * Parameters associated to this script execution
     *
     * @return
     */
    Map<String, Object> getContextParameters();

    /**
     * The resource resolver
     *
     * @return
     */
    ResourceResolver getResourceResolver();
}
