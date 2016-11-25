package biz.netcentric.aem.scriptconsole.groovy.extension.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;
import biz.netcentric.aem.scriptconsole.groovy.extension.BindingExtensionsProviderService;
import biz.netcentric.aem.scriptconsole.groovy.extension.binding.BindingExtension;
import biz.netcentric.aem.scriptconsole.groovy.extension.closure.ClosureBinding;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.PageManagerFactory;
import com.google.common.collect.Maps;
import groovy.lang.Binding;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service(value = BindingExtensionsProviderService.class)
@Component(immediate = true, metatype = false)
public class BindingExtensionsProviderRegistry implements BindingExtensionsProviderService {

    private final Logger LOG = LoggerFactory.getLogger(BindingExtensionsProviderRegistry.class);

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, referenceInterface = BindingExtension.class, policy = ReferencePolicy.DYNAMIC, bind = "bindBindingExtension", unbind = "unbindBindingExtension")
    private List<BindingExtension> bindingExtensions = new CopyOnWriteArrayList<>();

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, referenceInterface = ClosureBinding.class, policy = ReferencePolicy.DYNAMIC, bind = "bindClosureBinding", unbind = "unbindClosureBinding")
    private List<ClosureBinding> closureBindings = new CopyOnWriteArrayList<>();

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private QueryBuilder queryBuilder;

    private BundleContext bundleContext;

    @Activate
    protected void activate(final BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public Binding createBindings(final SlingHttpServletRequest request) {

        final BindingCommons bindingCommons = createBindingCommons(request);

        final Map<String, Object> allMappings = Maps.newHashMap();

        this.bindingExtensions.forEach(extension -> allMappings.putAll(extension.provideVariableMapping(bindingCommons)));

        final Binding binding = new Binding(allMappings);
        this.closureBindings.stream()
                .map(closureBinding -> closureBinding.getClosures(bindingCommons))
                .forEach(closuresCollection -> {
                    closuresCollection.forEach(closure -> binding.setVariable(closure.getBindingVariableName(), closure));
                });

        return binding;
    }

    public BindingCommons createBindingCommons(final SlingHttpServletRequest request) {
        final BindingCommons commons = new BindingCommons();

        final ResourceResolver resourceResolver = request.getResourceResolver();
        commons.setRequest(request);
        commons.setResourceResolver(resourceResolver);
        final Session session = resourceResolver.adaptTo(Session.class);
        commons.setSession(session);
        commons.setPageManager(pageManagerFactory.getPageManager(resourceResolver));
        commons.setQueryBuilder(queryBuilder);
        commons.setBundleContext(bundleContext);

        return commons;
    }

    public synchronized void bindBindingExtension(final BindingExtension extension) {
        bindingExtensions.add(extension);
        LOG.info("Added binding extension [{}]", extension.getClass().getName());
    }

    public synchronized void unbindBindingExtension(final BindingExtension extension) {
        bindingExtensions.remove(extension);
        LOG.info("Removed binding extension [{}]", extension.getClass().getName());
    }

    public synchronized void bindClosureBinding(final ClosureBinding closureBinding) {
        closureBindings.add(closureBinding);
        LOG.info("Added closure binding [{}]", closureBinding.getClass().getName());
    }

    public synchronized void unbindClosureBinding(final ClosureBinding closureBinding) {
        closureBindings.remove(closureBinding);
        LOG.info("Removed closure binding [{}]", closureBinding.getClass().getName());
    }
}
