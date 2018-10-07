package biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.binding.BindingExtension;
import com.google.common.collect.Maps;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * Provides binding to some of the most commonly used objects: <br />
 * SlingHttpServletRequest, ResourceResolver, Session, JackrabbitSession, PageManager, BundleContext, QueryBuilder
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Component(service = BindingExtension.class)
public class CommonServicesBinding implements BindingExtension {

    @Override
    public Map<String, Object> provideVariableMapping(final BindingCommons commons) {

        final Map<String, Object> bindingMap = Maps.newHashMap();
        this.registerObject(bindingMap, commons.getRequest(), "request");
        this.registerObject(bindingMap, commons.getResourceResolver(), "resourceResolver", "resolver");
        this.registerObject(bindingMap, commons.getSession(), "session");
        this.registerObject(bindingMap, commons.getJackrabbitSession(), "jackrabbitSession");
        this.registerObject(bindingMap, commons.getPageManager(), "pageManager");
        this.registerObject(bindingMap, commons.getBundleContext(), "bundleContext");
        this.registerObject(bindingMap, commons.getQueryBuilder(), "queryBuilder");
        this.registerObject(bindingMap, commons.getReplicator(), "replicator");

        return bindingMap;
    }
}
