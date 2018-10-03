package biz.netcentric.nclabs.groovyconsole.model;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * A persistable script
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface PersistableScript {

    /**
     * Get the script content
     * 
     * @return String script content
     */
    String getSourceCode();

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

    /**
     * Name of the script. Must be persistable in jcr.
     * 
     * @return String name of the script
     */
    String getName();

    /**
     * Path of the script.
     * 
     * @return String path of the script
     */
    String getPath();
}
