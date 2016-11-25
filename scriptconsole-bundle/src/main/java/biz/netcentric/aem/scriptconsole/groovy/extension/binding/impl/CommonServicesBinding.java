package biz.netcentric.aem.scriptconsole.groovy.extension.binding.impl;

import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;
import biz.netcentric.aem.scriptconsole.groovy.extension.binding.BindingExtension;
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

    @Override
    public Map<String, Object> provideVariableMapping(final BindingCommons commons) {

        final Map<String, Object> bindingMap = Maps.newHashMap();
        registerObject(bindingMap, commons.getRequest(), "request");
        registerObject(bindingMap, commons.getResourceResolver(), "resourceResolver", "resolver");
        registerObject(bindingMap, commons.getSession(), "session");
        registerObject(bindingMap, commons.getJackrabbitSession(), "jackrabbitSession");
        registerObject(bindingMap, commons.getPageManager(), "pageManager");
        registerObject(bindingMap, commons.getBundleContext(), "bundleContext");
        registerObject(bindingMap, commons.getQueryBuilder(), "queryBuilder");

        return bindingMap;
    }
}
