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
import org.apache.sling.api.resource.PersistenceException;
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

            final Resource scriptResource = createResource(request.getResourceResolver(), scriptName, scriptType);

            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Unable to create the script at the submitted location. Please check your arguments.");
    }

    private Resource createResource(final ResourceResolver resourceResolver, final String name, final String type)
            throws PersistenceException {
        final Resource newScript = resourceResolver.getResource("/myresource");
        final Map<String, Object> properties = Maps.newHashMap();

        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        properties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "/apps/aemscriptconsole/components/script");
        properties.put("scriptType", type);

        final Resource scriptResource = resourceResolver.create(newScript, JcrUtil.createValidName(name), properties);
        resourceResolver.commit();

        return scriptResource;
    }

    private Node createScriptNode(final ResourceResolver resourceResolver, final String path, final String type)
            throws RepositoryException, IOException {

        final Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) {
            throw new IllegalStateException("Unable to adapt resolver to session.");
        }

        final Optional<ScriptLanguage> extensionOptional = Stream.of(ScriptLanguage.values())
                .filter(l -> l.getExtension().equals(type))
                .findFirst();

        final String extension = extensionOptional.map(l -> l.getExtension()).orElse(ScriptLanguage.GROOVY.getExtension());

        Binary binary = createBindaryProperty(session, "println 'Hello World'");

        final Node node = session.getNode(path);
        node.setProperty("xyz", binary);
        session.save();

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
