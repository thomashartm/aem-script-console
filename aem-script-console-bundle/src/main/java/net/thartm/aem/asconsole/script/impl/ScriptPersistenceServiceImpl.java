package net.thartm.aem.asconsole.script.impl;

import com.google.common.collect.Maps;
import net.thartm.aem.asconsole.script.Script;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class ScriptPersistenceServiceImpl implements ScriptPersistenceService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean saveScript(final ResourceResolver resourceResolver, final Script script, final String locationPath)
            throws PersistenceException {

        // saves a script at the provided location
        final Resource resource = resourceResolver.getResource(locationPath);
        if (resource != null) {
            final Map<String, Object> properties = Maps.newHashMap();
            properties.put("fileExtension", script.getFileExtension());
            properties.put("script", script.getScriptContent());

            final Resource scriptResource = resourceResolver.create(resource, script.getName(), properties);
            LOG.debug("Saved script [{}]", scriptResource.getPath());

            return true;
        }

        return false;
    }

    @Override
    public Optional<Script> loadScript(final ResourceResolver resourceResolver, final String locationPath) {
        // loads a script from the provided location
        final Optional<Script> script = Optional.empty();
        return script;
    }

    @Override
    public List<Script> searchForScripts(final ResourceResolver resourceResolver, final String searchTerm) {
        // searches scripts that patch a particular pattern

        return Collections.EMPTY_LIST;
    }
}
