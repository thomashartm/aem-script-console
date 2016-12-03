package biz.netcentric.aem.scriptconsole.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import biz.netcentric.aem.scriptconsole.groovy.impl.ScriptConsoleConfiguration;
import com.day.cq.commons.jcr.JcrConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

/**
 * Persists and looks up script and form content and the associated metadata. <br />
 * Stores the data to the respurce matching the request URL. <br />
 * Requires appropriate permissions for the respective location.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@Deprecated
@SlingServlet(paths = { "/bin/netcentric/scriptconsole/save" }, methods = { "POST" }, extensions = { "json" }, metatype = false)
public class ScriptPersistenceServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    private static final String SCRIPT = "script";
    private static final String SCRIPT_TYPE = "scriptType";
    private static final String SCRIPT_NAME = "scriptName";
    private static final String SCRIPT_PATH = "savePath";

    @Reference
    private ScriptConsoleConfiguration scriptConsoleConfiguration;

    @Override
    public void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {

    }

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        if (isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not allowed to access this service");
        }

        final String scriptContent = request.getParameter(SCRIPT);
        final String scriptType = StringUtils.isNotEmpty(request.getParameter(SCRIPT_TYPE)) ? request.getParameter(SCRIPT_TYPE) : "groovy";
        final String scriptName = request.getParameter(SCRIPT_NAME);
        final String scriptPath = request.getParameter(SCRIPT_PATH);

        final ResourceResolver resourceResolver = request.getResourceResolver();
        final Resource saveTarget = resourceResolver.getResource(scriptPath);
        if (saveTarget != null) {
            final Resource scriptResource = saveTarget.getChild(scriptName);

            if (scriptResource != null) {
                updateScript(scriptResource, scriptContent, scriptType);
            } else {
                createScript(saveTarget, scriptName, scriptContent, scriptType);
            }
        }
    }

    private void createScript(final Resource parent, final String scriptName, final String scriptContent,
            final String scriptType) throws PersistenceException {

        final ResourceResolver resourceResolver = parent.getResourceResolver();

        final Map<String, Object> properties = Maps.newHashMap();
        properties.put("script", scriptContent);
        properties.put("contentType", scriptType);
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        resourceResolver.create(parent, scriptName, properties);

        if (resourceResolver.hasChanges()) {
            resourceResolver.commit();
        }
    }

    private void updateScript(final Resource scriptResource, final String scriptContent,
            final String scriptType) throws PersistenceException {

        final ResourceResolver resourceResolver = scriptResource.getResourceResolver();

        final ModifiableValueMap valueMap = scriptResource.adaptTo(ModifiableValueMap.class);
        if (valueMap != null) {
            valueMap.put("script", scriptContent);
            valueMap.put("contentType", scriptType);
            valueMap.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);

            if (resourceResolver.hasChanges()) {
                resourceResolver.commit();
            }
        }
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return scriptConsoleConfiguration.getAllowedGroups();
    }
}
