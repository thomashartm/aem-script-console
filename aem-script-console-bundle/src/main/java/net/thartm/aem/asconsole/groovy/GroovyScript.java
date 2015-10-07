package net.thartm.aem.asconsole.groovy;

import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.util.Assert;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScript implements Script {

    private static final String NAME_PATTERN = "%s.%s";

    private static final String FILE_EXT = "groovy";

    private String script;

    public GroovyScript(final String script) {
        this.script = script;
    }

    @Override
    public String getScript() {
        return this.script;
    }

    @Override
    public String save(final ResourceResolver resolver, final String location, final String name) throws PersistenceException {
        Assert.notNull(resolver);

        final Resource resource = resolver.getResource(location);
        Assert.notNull(resource, String.format("Resource for path [%s] does not exist.", location));

        final String nodeName = String.format(NAME_PATTERN, name, FILE_EXT);
        final Map<String, Object> properties = createPropertyMap(nodeName);

        final Resource newResource = resolver.create(resource, nodeName, properties);
        return newResource.getPath();
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

    public void setScript(final String script) {
        this.script = script;
    }


}
