package biz.netcentric.nclabs.groovyconsole.groovy.extension.impl;

import biz.netcentric.nclabs.groovyconsole.ScriptExecutionContext;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingExtensionsProviderService;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.BindingExtension;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.ClosureBinding;
import com.day.cq.replication.Replicator;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.PageManagerFactory;
import com.google.common.collect.Maps;
import groovy.lang.Binding;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Component(service = BindingExtensionsProviderService.class)
public class BindingExtensionsProviderRegistry implements BindingExtensionsProviderService {

    private final Logger LOG = LoggerFactory.getLogger(BindingExtensionsProviderRegistry.class);

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, service = BindingExtension.class, bind = "bindBindingExtension", unbind = "unbindBindingExtension")
    private final List<BindingExtension> bindingExtensions = new CopyOnWriteArrayList<>();

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, service = ClosureBinding.class, bind = "bindClosureBinding", unbind = "unbindClosureBinding")
    private final List<ClosureBinding> closureBindings = new CopyOnWriteArrayList<>();

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private JobManager jobManager;

    private BundleContext bundleContext;

    @Reference
    private Replicator replicator;

    @Activate
    protected void activate(final BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public Binding createBindings(final ScriptExecutionContext context) {

        final BindingCommons bindingCommons = this.createBindingCommons(context);

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

    public BindingCommons createBindingCommons(final ScriptExecutionContext context) {
        final BindingCommons commons = new BindingCommons();
        final ResourceResolver userResolver = context.getResourceResolver();
        final SlingHttpServletRequest request = context.getCurrentRequest();

        final ResourceResolver resourceResolver = (userResolver == null) ? request.getResourceResolver() : userResolver;
        commons.setRequest(request);
        commons.setResourceResolver(resourceResolver);
        final Session session = resourceResolver.adaptTo(Session.class);
        commons.setSession(session);
        commons.setPageManager(this.pageManagerFactory.getPageManager(resourceResolver));
        commons.setQueryBuilder(this.queryBuilder);
        commons.setBundleContext(this.bundleContext);
        commons.setJobManager(this.jobManager);
        commons.setReplicator(this.replicator);
        return commons;
    }

    public synchronized void bindBindingExtension(final BindingExtension extension) {
        this.bindingExtensions.add(extension);
        this.LOG.info("Added binding extension [{}]", extension.getClass().getName());
    }

    public synchronized void unbindBindingExtension(final BindingExtension extension) {
        this.bindingExtensions.remove(extension);
        this.LOG.info("Removed binding extension [{}]", extension.getClass().getName());
    }

    public synchronized void bindClosureBinding(final ClosureBinding closureBinding) {
        this.closureBindings.add(closureBinding);
        this.LOG.info("Added closure binding [{}]", closureBinding.getClass().getName());
    }

    public synchronized void unbindClosureBinding(final ClosureBinding closureBinding) {
        this.closureBindings.remove(closureBinding);
        this.LOG.info("Removed closure binding [{}]", closureBinding.getClass().getName());
    }
}
