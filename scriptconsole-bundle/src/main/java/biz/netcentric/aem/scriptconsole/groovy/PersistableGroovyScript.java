package biz.netcentric.aem.scriptconsole.groovy;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import biz.netcentric.aem.scriptconsole.PersistableScript;
import biz.netcentric.aem.scriptconsole.util.Assert;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class PersistableGroovyScript implements PersistableScript {

    private static final String NAME_PATTERN = "%s.%s";

    private static final String FILE_EXT = "groovy";

    private String script;

    private String name;

    private String path;

    public PersistableGroovyScript(final String script) {
        this.script = script;
    }

    public PersistableGroovyScript(final Resource resource) {
        final ValueMap valueMap = resource.getValueMap();
        this.script = valueMap.get("script", StringUtils.EMPTY);
        this.name = valueMap.get("name", StringUtils.EMPTY);
        this.path  = resource.getPath();
    }

    @Override
    public String getSourceCode() {
        return this.script;
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
        this.path = newResource.getPath();

        return this.path;
    }

    private Map<String, Object> createPropertyMap(final String nodeName) {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("type", FILE_EXT);
        properties.put("name", nodeName);
        properties.put("script", this.script);
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

    public void setScript(final String script) {
        this.script = script;
    }

}
