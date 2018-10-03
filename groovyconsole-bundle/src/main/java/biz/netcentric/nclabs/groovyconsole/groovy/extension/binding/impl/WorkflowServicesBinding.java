package biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.BindingExtension;
import com.google.common.collect.Maps;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import java.util.Map;

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
