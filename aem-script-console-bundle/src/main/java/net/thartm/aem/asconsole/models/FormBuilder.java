package net.thartm.aem.asconsole.models;

import com.google.common.collect.Lists;
import net.thartm.aem.asconsole.util.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class FormBuilder {

    public static final String ITEMS_NODE = "items";

    @SlingObject
    private SlingHttpServletRequest request;

    public Resource getFormResource() {
        final String pathFromSuffix = PathUtil.pathFromSuffix(request);
        if (StringUtils.isNotEmpty(pathFromSuffix)) {
            return this.request.getResourceResolver().getResource(pathFromSuffix);
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

    public List<Resource> getFormResources() {
        final Resource formResource = getFormResource();
        if (formResource != null) {
            final Resource items = formResource.getChild(ITEMS_NODE);
            if (items != null) {
                return Lists.newArrayList(items.listChildren());
            }
        }
        return Collections.emptyList();
    }

    public List<FormField> getFormFields() {
        final Resource formResource = getFormResource();
        if (formResource != null) {
            final Resource items = formResource.getChild(ITEMS_NODE);
            if (items != null) {
                final Spliterator<Resource> splitarator = Spliterators.spliteratorUnknownSize(items.listChildren(), Spliterator.ORDERED);
                return StreamSupport.stream(splitarator, false)
                        .filter(resource -> resource.getValueMap().containsKey(FormField.PN_META_TYPE))
                        .map(resource -> new FormField(resource))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}