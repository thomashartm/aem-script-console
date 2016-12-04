package biz.netcentric.aem.scriptconsole;

import java.util.List;
import java.util.Optional;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptLookupService {

    Optional<CustomScript> loadScript(ResourceResolver resourceResolver, String locationPath);

    List<Resource> findScripts(ResourceResolver resourceResolver, String... terms);
}
