package biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.BindableClosure;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.ClosureBinding;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.closure.ClosureExecutionRuntimeException;
import com.google.common.collect.Lists;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Collection;

/**
 * Get's a node using the session and path
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service
@Component(metatype = false)
public class GetNodeClosureBinding implements ClosureBinding {

    private final Logger LOG = LoggerFactory.getLogger(GetNodeClosure.class);

    @Override
    public Collection<BindableClosure> getClosures(final BindingCommons bindingCommons) {
        return Lists.newArrayList(new GetNodeClosure(bindingCommons, this, this));
    }

    static class GetNodeClosure extends BindableClosure {

        private static final String METHOD_NAME = "getNode";

        /**
         * Constructor
         *
         * @param owner
         * @param thisObject
         */
        public GetNodeClosure(final BindingCommons commons, final Object owner, final Object thisObject) {
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

            try {
                return getBindingCommons().getSession().getNode(path);
            } catch (RepositoryException e) {
                throw new ClosureExecutionRuntimeException(e, "Unable to get node with path [%s]", path);
            }
        }
    }
}
