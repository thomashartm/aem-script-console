package biz.netcentric.aem.scriptconsole.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.Page;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2016
 */
@Model(adaptables = { SlingHttpServletRequest.class})
public class Container {

    @SlingObject
    private SlingHttpServletRequest request;

    @ScriptVariable
    private Page currentPage;

    @SlingObject
    private Resource resource;

    public Page getCurrentPage() {
        return currentPage;
    }

    public Resource getResource() {
        return resource;
    }
}
