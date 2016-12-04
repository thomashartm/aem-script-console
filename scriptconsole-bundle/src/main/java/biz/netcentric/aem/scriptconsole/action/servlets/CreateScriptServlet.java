package biz.netcentric.aem.scriptconsole.action.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import biz.netcentric.aem.scriptconsole.action.ScriptLanguage;
import biz.netcentric.aem.scriptconsole.groovy.impl.ScriptConsoleConfiguration;
import biz.netcentric.aem.scriptconsole.servlets.AbstractJsonHandlerServlet;
import biz.netcentric.aem.scriptconsole.servlets.WithAccessCheck;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.sling.commons.mime.MimeTypeService;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
@SlingServlet(paths = { "/bin/netcentric/scriptconsole/create" }, methods = { "POST", "GET" }, extensions = {
        "json" }, metatype = false)
public class CreateScriptServlet extends AbstractJsonHandlerServlet implements WithAccessCheck {

    @Reference
    private ScriptConsoleConfiguration scriptConsoleConfiguration;

    @Override
    public void setCustomResponseHeaders(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
    }

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response, final ObjectMapper mapper)
            throws IOException, RepositoryException {
        // guard clause that checks wether the user has all required priviledges to access the service
        if (!isPriviledgedUser(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this service.");
        }

        final Resource locationResource = request.getRequestPathInfo().getSuffixResource();
        if (locationResource != null) {
            final String scriptName = request.getParameter("name");
            final String scriptType = request.getParameter("type");

            final Resource scriptResource = createScriptContainer(request.getResourceResolver(), locationResource, scriptName, scriptType);

            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Unable to create the script at the submitted location. Please check your arguments.");
    }

    private Resource createScriptContainer(final ResourceResolver resourceResolver, final Resource location, final String name, final String type)
            throws IOException, RepositoryException {


        final Map<String, Object> properties = Maps.newHashMap();
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        properties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "/apps/aemscriptconsole/components/script");
        properties.put("scriptType", type);

        final Resource scriptContainer = resourceResolver.create(location, JcrUtil.createValidName(name), properties);
        createScriptResource(resourceResolver, scriptContainer, type);

        resourceResolver.commit();

        return scriptContainer;
    }

    private void createScriptResource(final ResourceResolver resourceResolver, final Resource location, final String extension)
            throws IOException, RepositoryException {

        final Optional<ScriptLanguage> extensionOptional = Stream.of(ScriptLanguage.values())
                .filter(l -> l.getExtension().equals(extension))
                .findFirst();

        final ScriptLanguage language  = extensionOptional.map(l -> l).orElse(ScriptLanguage.GROOVY);

        final Map<String, Object> properties = Maps.newHashMap();
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_FILE);

        final String name = String.format("script.%s", language.getExtension());
        final Resource script = resourceResolver.create(location, name, properties);

        createBinaryProperty(resourceResolver, script, language);
    }

    private Node createBinaryProperty(final ResourceResolver resourceResolver, final Resource location, final ScriptLanguage language)
            throws RepositoryException, IOException {

        final Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) {
            throw new IllegalStateException("Unable to adapt resolver to session.");
        }

        final Binary binary = createBindaryProperty(session, "println 'Hello World'");

        final Node node = session.getNode(location.getPath());
        final Node jcrContentNode = node.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
        jcrContentNode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");
        jcrContentNode.setProperty(JcrConstants.JCR_ENCODING, "utf-8");
        jcrContentNode.setProperty(JcrConstants.JCR_DATA, binary);

        return node;
    }

    private Binary createBindaryProperty(final Session session, final String content) throws IOException, RepositoryException {
        final PipedInputStream pis = new PipedInputStream();
        final PipedOutputStream pos = new PipedOutputStream(pis);
        Executors.newSingleThreadExecutor().submit((Runnable) () -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(pos);
                writer.append(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return session.getValueFactory().createBinary(pis);
    }

    @Override
    public Collection<String> getEnabledGroups() {
        return scriptConsoleConfiguration.getAllowedGroups();
    }
}
