package biz.netcentric.aem.scriptconsole.models;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * FormField pojo representing a form field persisted as a resource below /etc/aemscriptconsole/..../yourform/items/*.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2016
 */
public class FormField {

    public static final String PN_META_TYPE = "metaType";

    private String path;

    private String name;

    private String type;

    private Resource resource;

    /**
     * Constructor
     * 
     * @param resource The field resource
     */
    public FormField(final Resource resource) {
        this.name = resource.getName();
        this.path = resource.getPath();
        final ValueMap valueMap = resource.getValueMap();
        this.type = valueMap.get(PN_META_TYPE, StringUtils.EMPTY);
        this.resource = resource;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public Resource getResource() {
        return resource;
    }
}
