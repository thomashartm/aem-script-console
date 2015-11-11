package net.thartm.aem.asconsole.extension.binding.impl;

import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.google.common.collect.Maps;
import groovy.lang.Binding;
import net.thartm.aem.asconsole.extension.binding.BindingExtension;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Session;
import java.util.Map;

/**
 * Provides binding to workflow related objects: <br />
 * WorkflowService, WorkflowSession
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service
@Component(immediate = true)
public class WorkflowServicesBinding implements BindingExtension {

    @Reference
    private WorkflowService workflowService;

    @Override
    public Map<String, Object> provideVariableMapping(final SlingHttpServletRequest request) {
        final Map<String, Object> bindingMap = Maps.newHashMap();

        final ResourceResolver resourceResolver = request.getResourceResolver();
        final Session session = resourceResolver.adaptTo(Session.class);

        final WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
        registerObject(bindingMap, workflowService, "workflowService");
        registerObject(bindingMap, workflowSession, "workflowSession");

        return bindingMap;
    }
}
