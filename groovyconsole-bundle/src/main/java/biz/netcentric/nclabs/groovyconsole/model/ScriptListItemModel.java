package biz.netcentric.nclabs.groovyconsole.model;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Resource based entity inside the script persistence location which represents either a folder or a script.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 0/2018
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ScriptListItemModel {

    @Inject
    @Named("jcr:title")
    @Optional
    private String title;

    @Inject
    @Named("jcr:description")
    @Optional
    private String description;

    @Inject
    @SlingObject
    private Resource resource;

    @Inject
    @SlingObject
    private SlingHttpServletRequest request;

    /**
     * Provides the jcr:title or the name if title is missing or empty.
     *
     * @return String
     */
    public String getTitle() {
        return StringUtils.isEmpty(title) ? getName() : title;
    }

    /**
     * Provides the jcr:description or empty string
     *
     * @return String
     */
    public String getDescription() {
        return StringUtils.isEmpty(description) ? StringUtils.EMPTY : description;
    }

    public boolean isHasDescription() {
        return StringUtils.isNotEmpty(description);
    }

    /**
     * The resource path
     *
     * @return String
     */
    public String getPath() {
        return resource.getPath();
    }

    /**
     * Provides the resource name
     *
     * @return String
     */
    public String getName() {
        return resource.getName();
    }

    public String getIcon() {
        if (isFolder()) {
            return Icon.FOLDER.getName();
        }
        return Icon.FILE.getName();
    }

    /**
     * The resource type of the script node.
     *
     * @return
     */
    public String getType() {
        final ResourceResolver resourceResolver = resource.getResourceResolver();
        if (resourceResolver == null) {
            throw new IllegalStateException("Unable to get resolver");
        }

        final Resource currentResource = resourceResolver.getResource(getPath());
        return currentResource != null ? currentResource.getResourceType() : StringUtils.EMPTY;
    }

    /**
     * Is the node a folder or a script file
     *
     * @return True or false
     */
    public boolean isFolder() {
        return resource.isResourceType(JcrResourceConstants.NT_SLING_FOLDER) || resource.isResourceType(JcrConstants.NT_FOLDER);
    }
}
