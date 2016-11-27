package biz.netcentric.aem.scriptconsole.job;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;

import java.util.Map;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service
@Component
@Properties({
        @Property(name = JobConsumer.PROPERTY_TOPICS, value = ScriptJobConsumer.JOB_TOPIC + "/**", propertyPrivate = true)
})
public class ScriptJobConsumer implements JobConsumer, WithServiceResourceResolver {

    public static final String JOB_TOPIC = "biz/netcentric/aem/scriptconsole/job/scriptedjob";

    private static final String SCRIPT_PATH = "scriptPath";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public JobResult process(final Job job) {

        final String path = job.getProperty(SCRIPT_PATH, StringUtils.EMPTY);
        if(StringUtils.isNotBlank(path)){

            ResourceResolver resourceResolver = null;
            try {
                resourceResolver = createResourceResolver();

                // TODO create the script context
                // TODO execute the script now
            } catch (LoginException e) {
                // TODO proper exeception handling
                e.printStackTrace();
            }finally{
                closeSilently(resourceResolver);
            }
        }
        return JobResult.FAILED;
    }

    @Override
    public ResourceResolverFactory getResourceResolverFactory() {
        return this.resourceResolverFactory;
    }
}
