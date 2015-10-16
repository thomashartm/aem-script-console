package net.thartm.aem.asconsole.script;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * A persistable script
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface Script {

    /**
     * Get the script content
     * 
     * @return String script content
     */
    String getScriptContent();

    /**
     * Saves this script at the given location.
     *
     * @param resolver The ResourceResolver
     * @param location A path like e.g. /etc/asconsole/scripts
     * @param name The name without file extension. The concrete implementation tales care of it
     * @return Complete path to the script
     */
    String save(final ResourceResolver resolver, final String location, final String name) throws PersistenceException;

    /**
     * The file extension represneting the script type
     * 
     * @return String file extension e.g. .groovy or .js
     */
    String getFileExtension();
}
