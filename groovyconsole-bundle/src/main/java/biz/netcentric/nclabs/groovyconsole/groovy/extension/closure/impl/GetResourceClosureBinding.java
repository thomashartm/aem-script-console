package biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.BindableClosure;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.ClosureBinding;
import com.google.common.collect.Lists;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Component(service = ClosureBinding.class)
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
        public java.lang.Object doCall(Object... args) {
            final String path = (String) args[0];
            return getBindingCommons().getResourceResolver().getResource(path);
        }

    }
}