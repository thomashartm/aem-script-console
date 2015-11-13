package net.thartm.aem.asconsole.extension.binding.impl;

import java.util.Map;

import javax.jcr.Session;

import net.thartm.aem.asconsole.extension.binding.BindingExtension;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import com.google.common.collect.Maps;

/**
 * Provides binding to workflow related objects: <br />
 * WorkflowService, WorkflowSession
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service
@Component
public class WorkflowServicesBinding implements BindingExtension {



    @Override
    public Map<String, Object> provideVariableMapping(final SlingHttpServletRequest request) {
        final Map<String, Object> bindingMap = Maps.newHashMap();

        final ResourceResolver resourceResolver = request.getResourceResolver();
        final Session session = resourceResolver.adaptTo(Session.class);



        return bindingMap;
    }
}
