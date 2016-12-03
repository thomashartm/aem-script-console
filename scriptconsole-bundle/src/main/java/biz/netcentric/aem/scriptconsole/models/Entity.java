package biz.netcentric.aem.scriptconsole.models;

import javax.inject.Inject;
import javax.inject.Named;

import com.day.cq.i18n.I18n;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * Resource based entity inside the script persistence location.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2016
 */
@Model(adaptables = Resource.class)
public class Entity {

    @Inject
    @Named("jcr:title")
    @Optional
    private String title;

    @Inject
    @SlingObject
    private Resource resource;

    public String getTitle() {
        return StringUtils.isEmpty(title) ? getName() : title;
    }

    public String getPath() {
        return resource.getPath();
    }

    public String getName() {
        return resource.getName();
    }

    public String getIcon() {
        if (isFolder()) {
            return Icon.FOLDER.getName();
        }
        return Icon.FILE.getName();
    }

    public boolean isFolder() {
        return resource.isResourceType("sling:folder") || resource.isResourceType("nt:folder");
    }
}
