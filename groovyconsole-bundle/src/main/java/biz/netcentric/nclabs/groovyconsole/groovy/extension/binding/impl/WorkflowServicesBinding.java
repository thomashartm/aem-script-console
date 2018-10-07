package biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.BindingExtension;
import com.google.common.collect.Maps;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * Provides binding to workflow related objects: <br />
 * WorkflowService, WorkflowSession
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Component(service = BindingExtension.class)
public class WorkflowServicesBinding implements BindingExtension {

    @Override
    public Map<String, Object> provideVariableMapping(final BindingCommons bindingCommons) {
        final Map<String, Object> bindingMap = Maps.newHashMap();

        return bindingMap;
    }
}
