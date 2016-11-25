package biz.netcentric.aem.scriptconsole.groovy.extension.closure.impl;

import java.util.Collection;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.aem.scriptconsole.groovy.extension.BindingCommons;
import biz.netcentric.aem.scriptconsole.groovy.extension.closure.ClosureBinding;
import com.google.common.collect.Lists;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service
@Component(metatype = false)
public class GetResourceClosureBinding implements ClosureBinding {

    private final Logger LOG = LoggerFactory.getLogger(GetResourceClosureBinding.class);

    @Override
    public Collection<BindableClosure> getClosures(final BindingCommons bindingCommons) {
        return Lists.newArrayList(new GetResourceClosure(bindingCommons, this, this));
    }

    static class GetResourceClosure extends BindableClosure {

        private static final String METHOD_NAME = "getResource";

        /**
         * Constructor
         *
         * @param owner
         * @param thisObject
         */
        public GetResourceClosure(final BindingCommons commons, final Object owner, final Object thisObject) {
            super(METHOD_NAME, commons, owner, thisObject);
            parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            maximumNumberOfParameters = 1;
        }

        /**
         * Method call
         *
         * @param args
         * @return
         */
        public Object doCall(Object... args) {
            final String path = (String) args[0];
            return getBindingCommons().getResourceResolver().getResource(path);
        }

    }
}