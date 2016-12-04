package biz.netcentric.aem.scriptconsole.action;

import com.adobe.granite.workflow.WorkflowException;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
public class Script {

    private static final String RESOURCE_TYPE_SCRIPT = "/apps/aemscriptconsole/components/script";
    private static final String PN_SCRIPT_TYPE = "scriptType";

    private String name;

    private String location;

    private ScriptLanguage language;

    private String scriptContent;

    private boolean justPersisted = false;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public ScriptLanguage getLanguage() {
        return language;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public boolean isJustPersisted() {
        return justPersisted;
    }

    public boolean exists(final ResourceResolver resourceResolver){
        final Resource locationResource = resourceResolver.getResource(getLocation());
        return locationResource.getChild(JcrUtil.createValidName(name)) != null;
    }

    public Resource create(final ResourceResolver resourceResolver) throws IOException, RepositoryException {
        final Map<String, Object> properties = createContainerProperties();

        final Resource locationResource = resourceResolver.getResource(getLocation());
        final Resource scriptContainer = resourceResolver.create(locationResource, JcrUtil.createValidName(name), properties);
        createScriptResource(resourceResolver, scriptContainer, getLanguage().getExtension());

        this.justPersisted = true;

        return scriptContainer;
    }

    private final Map<String, Object> createContainerProperties() {
        final Map<String, Object> properties = Maps.newHashMap();
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        properties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, RESOURCE_TYPE_SCRIPT);
        properties.put(PN_SCRIPT_TYPE, getLanguage().getExtension());

        return properties;
    }

    private Resource createScriptResource(final ResourceResolver resourceResolver, final Resource location, final String extension)
            throws IOException, RepositoryException {

        final Map<String, Object> properties = Maps.newHashMap();
        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_FILE);

        final String name = String.format("script.%s", language.getExtension());
        final Resource script = resourceResolver.create(location, name, properties);
        addScriptData(resourceResolver, script, language);
        resourceResolver.commit();

        return script;
    }

    private Node addScriptData(final ResourceResolver resourceResolver, final Resource location, final ScriptLanguage language)
            throws RepositoryException, IOException {

        final Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) {
            throw new IllegalStateException("Unable to adapt resolver to session.");
        }

        final Binary binary = createBinaryProperty(session, "println 'Hello World'");

        final Node node = session.getNode(location.getPath());
        final Node jcrContentNode = node.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
        jcrContentNode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");
        jcrContentNode.setProperty(JcrConstants.JCR_ENCODING, "utf-8");
        jcrContentNode.setProperty(JcrConstants.JCR_DATA, binary);

        return node;
    }

    private Binary createBinaryProperty(final Session session, final String content) throws IOException, RepositoryException {
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

    public static final class Builder {

        private String name;

        private String location;

        private ScriptLanguage language;

        private String scriptContent;

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withLocation(final String location) {
            this.location = location;
            return this;
        }

        public Builder withLanguage(final String language) {
            final Optional<ScriptLanguage> extensionOptional = Stream.of(ScriptLanguage.values())
                    .filter(l -> l.getExtension().equals(language)).findFirst();

            this.language = extensionOptional.map(l -> l).orElse(ScriptLanguage.GROOVY);
            return this;
        }

        public Builder withScriptContent(final String scriptContent) {
            this.scriptContent = scriptContent;
            return this;
        }

        /**
         * @return
         * @throws WorkflowException
         */
        public Script build() {
            final Script script = new Script();
            script.name = name;
            script.location = location;
            script.language = (language != null) ? language : ScriptLanguage.GROOVY;
            script.scriptContent = (StringUtils.isNotBlank(scriptContent)) ? scriptContent : "println \"Hello World!\"";

            return script;
        }
    }
}
