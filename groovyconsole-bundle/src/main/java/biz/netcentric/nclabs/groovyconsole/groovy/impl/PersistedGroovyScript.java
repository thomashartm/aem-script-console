package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import biz.netcentric.nclabs.groovyconsole.util.Assert;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * Groovy script which is persisted and retrieved inside of a repository location.
 * Any script which is supposed to be executed by the script shell and a eventually a non technical user must be a persisted PersistedGroovyScript.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class PersistedGroovyScript implements GroovyScript {

    private static final Logger LOG = LoggerFactory.getLogger(PersistedGroovyScript.class);

    private static final String NAME_PATTERN = "%s.%s";

    private static final String FILE_EXT = "groovy";

    private static final String DEFAULT_SCRIPT_CONTAINER_NAME = "script";

    private static final String DEFAULT_SCRIPT_NODE = "/script.groovy";

    private final Resource scriptResource;

    private final Resource containerResource;

    private String scriptSource;

    private String name;

    private String path;

    private String userName;

    private String description;

    private String title;

    private String user;

    /**
     * A groovy script that can be read or persisted.
     *
     * @param resource
     * @throws IOException
     */
    public PersistedGroovyScript(final Resource resource) throws IOException {
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

    @Override
    public String getExecutionUser() {
        return user;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
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
