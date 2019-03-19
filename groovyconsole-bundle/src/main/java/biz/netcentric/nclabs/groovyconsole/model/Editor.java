package biz.netcentric.nclabs.groovyconsole.model;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * Editor metainformation
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class Editor {

    @SlingObject
    private Resource resource;

    @SlingObject
    private SlingHttpServletRequest request;

    private String scriptPath;

    private String defaultName;

    /**
     * Provides a default name for the script.
     *
     * @return
     */
    public String getDefaultName() {
        if(StringUtils.isBlank(this.defaultName)){
            return RandomStringUtils.randomAlphabetic(10);
        }
        return defaultName;
    }

    /**
     * Script path for an existing script
     *
     * @return String
     */
    public String getScriptPath() {
        if (StringUtils.isEmpty(this.scriptPath)) {
            this.scriptPath = request.getRequestPathInfo().getSuffix();
            if (StringUtils.isEmpty(this.scriptPath)) {
                return StringUtils.EMPTY;
            }
        }

        return this.scriptPath;
    }

    /**
     * Has a script path
     *
     * @return boolean
     */
    public boolean isWithScriptPath() {
        final String suffix = getScriptPath();
        return StringUtils.isNotEmpty(suffix);
    }
}
