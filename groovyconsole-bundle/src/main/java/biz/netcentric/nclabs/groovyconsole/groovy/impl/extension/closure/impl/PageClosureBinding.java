package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.BindableClosure;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.ClosureBinding;
import com.google.common.collect.Lists;
import org.osgi.service.component.annotations.Component;

import java.util.Collection;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */

@Component(service = ClosureBinding.class)
public class PageClosureBinding implements ClosureBinding {

    @Override
    public Collection<BindableClosure> getClosures(final BindingCommons bindingCommons) {
        return Lists.newArrayList(new GetPageClosure(bindingCommons, this, this));
    }

    static class GetPageClosure extends BindableClosure {

        private static final String METHOD_NAME = "getPage";

        /**
         * Constructor
         *
         * @param owner
         * @param thisObject
         */
        public GetPageClosure(final BindingCommons commons, final Object owner, final Object thisObject) {
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
            return getBindingCommons().getPageManager().getPage(path);
        }
    }
}
