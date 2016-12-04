package biz.netcentric.aem.scriptconsole;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * A persistable script
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface CustomScript {

    /**
     * Get the script content
     * 
     * @return String script content
     */
    String getSourceCode();

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
