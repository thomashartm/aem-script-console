package net.thartm.aem.asconsole.groovy.extension.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import net.thartm.aem.asconsole.groovy.extension.binding.BindingExtension;
import net.thartm.aem.asconsole.groovy.extension.BindingExtensionsProviderService;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service(value = BindingExtensionsProviderService.class)
@Component(immediate = true, metatype = false)
public class BindingExtensionsProviderRegistry implements BindingExtensionsProviderService {

    private final Logger LOG = LoggerFactory.getLogger(BindingExtensionsProviderRegistry.class);

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, referenceInterface = BindingExtension.class,
            policy = ReferencePolicy.DYNAMIC, bind = "bindBindingExtension", unbind = "unbindBindingExtension")
    private List<BindingExtension> bindingExtensions = new CopyOnWriteArrayList<>();

    public Binding getExtensionsBinding(final SlingHttpServletRequest request) {

        final Map<String, Object> allMappings = Maps.newHashMap();
        this.bindingExtensions.forEach(extension -> allMappings.putAll(extension.provideVariableMapping(request)));

        return new Binding(allMappings);
    }


    public synchronized void bindBindingExtension(BindingExtension extension) {
        bindingExtensions.add(extension);

        LOG.info("Added binding extension [{}]", extension.getClass().getName());
    }

    public synchronized void unbindBindingExtension(BindingExtension extension) {
        bindingExtensions.remove(extension);

        LOG.info("Removed binding extension [{}]", extension.getClass().getName());
    }
}
