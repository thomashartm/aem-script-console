package biz.netcentric.aem.scriptconsole.groovy;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import biz.netcentric.aem.scriptconsole.CustomScript;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class CustomGroovyScript implements CustomScript {

    private static final String NAME_PATTERN = "%s.%s";

    private static final String FILE_EXT = "groovy";

    private String script;

    private String name;

    private String path;

    public CustomGroovyScript(final String script) {
        this.script = script;
    }

    public CustomGroovyScript(final Resource resource) {
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
