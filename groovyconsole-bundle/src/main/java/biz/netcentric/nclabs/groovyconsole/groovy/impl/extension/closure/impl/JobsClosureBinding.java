package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.BindingCommons;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.BindableClosure;
import biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure.ClosureBinding;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;

import java.util.Collection;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
@Component(service = ClosureBinding.class)
public class JobsClosureBinding implements ClosureBinding {

    @Override
    public Collection<BindableClosure> getClosures(final BindingCommons bindingCommons) {
        return Lists.newArrayList(new JobsClosure(bindingCommons, this, this));
    }

    static class JobsClosure extends BindableClosure {

        private static final String METHOD_NAME = "getJobs";

        /**
         * Constructor
         *
         * @param owner
         * @param thisObject
         */
        public JobsClosure(final BindingCommons commons, final Object owner, final Object thisObject) {
            super(METHOD_NAME, commons, owner, thisObject);
            parameterTypes = new Class[2];
            parameterTypes[0] = JobManager.QueryType.class;
            parameterTypes[1] = String.class;
            maximumNumberOfParameters = 2;
        }

        /**
         * Method call
         *
         * @param args
         * @return
         */
        public java.lang.Object doCall(Object... args) {
            final JobManager.QueryType queryType = (JobManager.QueryType) args[0];
            final String topic = (String) args[1];
            return getBindingCommons().getJobManager().findJobs(queryType, topic, -1, Maps.newHashMap());
        }
    }
}
