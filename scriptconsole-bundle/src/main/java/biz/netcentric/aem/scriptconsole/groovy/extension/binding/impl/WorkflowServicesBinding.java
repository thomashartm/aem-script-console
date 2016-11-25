package biz.netcentric.aem.scriptconsole.groovy.extension.binding.impl;

import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;
import biz.netcentric.aem.scriptconsole.groovy.extension.binding.BindingExtension;
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
    public Map<String, Object> provideVariableMapping(final BindingCommons bindingCommons) {
        final Map<String, Object> bindingMap = Maps.newHashMap();

        return bindingMap;
    }
}
