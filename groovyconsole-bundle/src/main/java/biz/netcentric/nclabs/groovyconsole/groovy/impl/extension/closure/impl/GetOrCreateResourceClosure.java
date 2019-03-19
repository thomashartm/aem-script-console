package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.BindableClosure;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.ClosureBinding;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.ClosureExecutionRuntimeException;
import com.google.common.collect.Lists;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Gets org.apache.sling.api.resource.Resource, or creates it, if not existing, according to passed properties map
 *
 * @author jochen.koschorke
 * @since 11/2016
 */
@Component(service = ClosureBinding.class)
public class GetOrCreateResourceClosure implements ClosureBinding {

    private final Logger LOG = LoggerFactory.getLogger(GetOrCreateResourceClosure.class);

    @Override
    public Collection<BindableClosure> getClosures(final BindingCommons bindingCommons) {
        return Lists.newArrayList(new GetOrCreateResource(bindingCommons, this, this));
    }

    static class GetOrCreateResource extends BindableClosure {

        private static final String METHOD_NAME = "getOrCreateResource";

        /**
         * Constructor
         *
         * @param owner
         * @param thisObject
         */
        public GetOrCreateResource(final BindingCommons commons, final Object owner, final Object thisObject) {
            super(METHOD_NAME, commons, owner, thisObject);
            this.parameterTypes = new Class[3];
            this.parameterTypes[0] = Resource.class;
            this.parameterTypes[1] = String.class;
            this.parameterTypes[2] = Map.class;
            this.maximumNumberOfParameters = 3;
        }

        /**
         * Method call
         *
         * @param args
         * @return
         */
        public java.lang.Object doCall(final Object... args) {
            final Resource parent = (Resource) args[0];
            final String name = (String) args[1];
            final Map<String, Object> properties = (Map<String, Object>) args[2];

            try {
                Resource resourceToGetOrCreate = parent.getChild(name);
                if (resourceToGetOrCreate == null) {
                    resourceToGetOrCreate = this.getBindingCommons().getResourceResolver().create(parent, name, properties);

                }
                return resourceToGetOrCreate;
            } catch (final PersistenceException e) {
                throw new ClosureExecutionRuntimeException(e, "Unable to get or create resource with path [%s] under parent [%s]", name,
                        parent.getPath());
            }
        }
    }
}
