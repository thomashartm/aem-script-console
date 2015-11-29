package net.thartm.aem.asconsole.script.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.Map;

/**
 * Persists and looks up script and form content and the associated metadata. <br />
 * Stores the data to the respurce matching the request URL. <br />
 * Requires appropriate permissions for the respective location.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@SlingServlet(selectors = "savescript", methods = { "POST" }, extensions = { "json" }, metatype = true)
public class ScriptPersistenceServlet extends AbstractJsonPostHandlerServlet {

    private static final String SCRIPT = "script";
    private static final String SCRIPT_TYPE = "scriptType";
    private static final String SCRIPT_NAME = "scriptName";

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        final String scriptContent = request.getParameter(SCRIPT);
        final String scriptType = request.getParameter(SCRIPT_TYPE);
        final String scriptName = request.getParameter(SCRIPT_NAME);

        final Resource saveTarget = request.getResource();
        final Resource scriptResource = saveTarget.getChild(scriptName);

        if (scriptResource != null) {
            updateScript(scriptResource, scriptContent, scriptType);
        } else {
            createScript(saveTarget, scriptName, scriptContent, scriptType);
        }
    }

    private void createScript(final Resource parent, final String scriptName, final String scriptContent,
            final String scriptType) throws PersistenceException {

        final ResourceResolver resourceResolver = parent.getResourceResolver();

        final Map<String, Object> properties = Maps.newHashMap();
        properties.put("script", scriptContent);
        properties.put("contentType", scriptType);
        resourceResolver.create(parent, scriptName, properties);

        if (resourceResolver.hasChanges()) {
            resourceResolver.commit();
        }
    }

    private void updateScript(final Resource scriptResource, final String scriptContent,
            final String scriptType) throws PersistenceException {

        final ResourceResolver resourceResolver = scriptResource.getResourceResolver();

        final ModifiableValueMap valueMap = scriptResource.adaptTo(ModifiableValueMap.class);
        valueMap.put("script", scriptContent);
        valueMap.put("contentType", scriptType);

        if (resourceResolver.hasChanges()) {
            resourceResolver.commit();
        }
    }
}
