package biz.netcentric.nclabs.groovyconsole.groovy;

import biz.netcentric.nclabs.groovyconsole.model.PersistableScript;
import biz.netcentric.nclabs.groovyconsole.util.Assert;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class PersistableGroovyScript implements PersistableScript {

    private static final Logger LOG = LoggerFactory.getLogger(PersistableGroovyScript.class);

    private static final String NAME_PATTERN = "%s.%s";

    private static final String FILE_EXT = "groovy";

    private static final String DEFAULT_SCRIPT_NODE = "/script.groovy";

    private final Resource scriptResource;

    private final Resource containerResource;

    private String scriptSource;

    private String name;

    private String path;

    private String userName;

    /**
     * A groovy script that can be read or persisted.
     *
     * @param resource
     * @throws IOException
     */
    public PersistableGroovyScript(final Resource resource) throws IOException {
        final boolean isScriptResource = StringUtils.endsWith(resource.getName(), FILE_EXT);

        this.scriptResource = isScriptResource ? resource : retrieveFileResourceChild(resource);
        Assert.notNull(this.scriptResource);
        this.containerResource = isScriptResource ? resource.getParent() : resource;
        Assert.notNull(this.containerResource);

        this.path = resource.getPath();

        final ValueMap metaData = this.containerResource.getValueMap();
        this.name = metaData.get("name", this.containerResource.getName());
        this.userName = metaData.get("user", StringUtils.EMPTY);

        this.scriptSource = retrieveScriptSource();
    }

    private String retrieveScriptSource() throws IOException {
        final Resource contentResource = scriptResource.getChild(org.apache.jackrabbit.JcrConstants.JCR_CONTENT);

        if (contentResource != null) {
            try (InputStream is = contentResource.adaptTo(InputStream.class); BufferedInputStream bin = new BufferedInputStream(is)) {
                final String scriptSource = IOUtils.toString(bin, CharEncoding.UTF_8);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("scriptSource=[{}]", this.scriptSource);
                }
                return scriptSource;
            }
        }

        return StringUtils.EMPTY;
    }

    private Resource retrieveFileResourceChild(final Resource resource) {
        final Iterator<Resource> children = resource.listChildren();
        final Spliterator<Resource> splitarator = Spliterators.spliteratorUnknownSize(children, Spliterator.DISTINCT);

        final Optional<Resource> scriptResourceOptional = StreamSupport.stream(splitarator, false)
                .filter(child -> isFileResource(child))
                .findFirst();

        return scriptResourceOptional.orElse(this.createDummyResource(resource));
    }

    private NonExistingResource createDummyResource(Resource resource) {
        return new NonExistingResource(resource.getResourceResolver(), resource.getPath() + DEFAULT_SCRIPT_NODE);
    }

    private boolean isFileResource(Resource child) {
        return StringUtils.equals((String) child.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE), JcrConstants.NT_FILE);
    }

    @Override
    public String getSourceCode() {
        return this.scriptSource;
    }

    @Override
    public String save(final ResourceResolver resolver, final String location, final String name) throws PersistenceException {
        Assert.notNull(resolver);

        final Resource resource = resolver.getResource(location);
        Assert.notNull(resource, String.format("Resource for path [%s] does not exist.", location));

        this.name = name;

        final String nodeName = String.format(NAME_PATTERN, name, FILE_EXT);
        final Map<String, Object> properties = createPropertyMap(nodeName);

        final Resource newResource = resolver.create(resource, nodeName, properties);
        return newResource.getPath();
    }

    private Map<String, Object> createPropertyMap(final String nodeName) {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("type", FILE_EXT);
        properties.put("name", nodeName);
        properties.put("script", this.scriptSource);
        return properties;
    }

    @Override
    public String getFileExtension() {
        return FILE_EXT;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * Get's the user name
     *
     * @return
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Set's the script source
     *
     * @param scriptSource String source
     */
    public void setScriptSource(final String scriptSource) {
        this.scriptSource = scriptSource;
    }
}
