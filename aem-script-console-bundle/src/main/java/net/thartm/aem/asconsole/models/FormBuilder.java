package net.thartm.aem.asconsole.models;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.util.Collections;
import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class FormBuilder {

    @SlingObject
    private SlingHttpServletRequest request;

    private String getSuffix() {
        final String suffix = request.getRequestPathInfo().getSuffix();
        return StringUtils.isNotEmpty(suffix) ? suffix : StringUtils.EMPTY;
    }

    public Resource getFormResource() {
        final String suffix = getSuffix();
        if (StringUtils.isNotEmpty(suffix)) {
            return this.request.getResourceResolver().getResource(suffix);
        }
        return new NonExistingResource(this.request.getResourceResolver(), StringUtils.EMPTY);
    }

    public String getFormPath() {
        final Resource resource = getFormResource();
        if (ResourceUtil.isNonExistingResource(resource)) {
            return resource.getPath();
        }
        return StringUtils.EMPTY;
    }

    public List<Resource> getFormElements(){

        return Collections.emptyList();
    }

}