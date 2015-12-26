package net.thartm.aem.asconsole.script.impl;

import net.thartm.aem.asconsole.script.Script;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;
import java.util.Optional;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptLookupService {

    @Deprecated
    boolean saveScript(ResourceResolver resourceResolver, Script script, String locationPath)
            throws PersistenceException;

    Optional<Script> loadScript(ResourceResolver resourceResolver, String locationPath);

    List<Script> searchForScripts(ResourceResolver resourceResolver, String searchTerm);
}
