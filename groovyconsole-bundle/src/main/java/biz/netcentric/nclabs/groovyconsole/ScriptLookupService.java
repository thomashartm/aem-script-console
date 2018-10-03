package biz.netcentric.nclabs.groovyconsole;

import biz.netcentric.nclabs.groovyconsole.model.PersistableScript;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;
import java.util.Optional;

/**
 * Provides access to scripts
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptLookupService {

    /**
     * Retrieves a script by it's path.
     *
     * @param resourceResolver Resolver
     * @param locationPath     Path of the script
     * @return Script entity
     */
    Optional<PersistableScript> loadScript(ResourceResolver resourceResolver, String locationPath);

    /**
     * Finds scripts by a search term.
     *
     * @param resourceResolver Resolver
     * @param terms            String Terms
     * @return List of resources
     */
    List<Resource> findScripts(ResourceResolver resourceResolver, String... terms);
}
