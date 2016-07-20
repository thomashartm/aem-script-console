package net.thartm.aem.asconsole.groovy.extension.binding.impl;

import java.util.Map;

import javax.jcr.Session;

import net.thartm.aem.asconsole.groovy.extension.binding.BindingExtension;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;

import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.google.common.collect.Maps;

/**
 * Provides binding to some of the most commonly used objects: <br />
 * SlingHttpServletRequest, ResourceResolver, Session, JackrabbitSession, PageManager, BundleContext, QueryBuilder
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service
@Component
public class CommonServicesBinding implements BindingExtension {

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private QueryBuilder queryBuilder;

    private BundleContext bundleContext;

    @Activate
    protected void activate(final BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public Map<String, Object> provideVariableMapping(final SlingHttpServletRequest request) {

        final Map<String, Object> bindingMap = Maps.newHashMap();
        registerObject(bindingMap, request, "request");

        final ResourceResolver resourceResolver = request.getResourceResolver();
        registerObject(bindingMap, resourceResolver, "resourceResolver", "resolver");

        final Session session = resourceResolver.adaptTo(Session.class);
        registerObject(bindingMap, session, "session");

        final JackrabbitSession jackrabbitSession = resourceResolver.adaptTo(JackrabbitSession.class);
        registerObject(bindingMap, jackrabbitSession, "jackrabbitSession");

        final PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
        registerObject(bindingMap, pageManager, "pageManager");

        registerObject(bindingMap, bundleContext, "bundleContext");

        registerObject(bindingMap, queryBuilder, "queryBuilder");

        return bindingMap;
    }
}
