package net.thartm.aem.asconsole.script;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;
import java.util.Optional;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptLookupService {

    Optional<Script> loadScript(ResourceResolver resourceResolver, String locationPath);

    List<Resource> findScripts(ResourceResolver resourceResolver, String... terms);
}
