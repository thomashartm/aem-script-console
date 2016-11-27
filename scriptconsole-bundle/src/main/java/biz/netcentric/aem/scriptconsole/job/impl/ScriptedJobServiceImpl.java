package biz.netcentric.aem.scriptconsole.job.impl;

import biz.netcentric.aem.scriptconsole.PersistableScript;
import biz.netcentric.aem.scriptconsole.ScriptContext;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.event.jobs.JobManager;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service
@Component(metatype = false)
public class ScriptedJobServiceImpl {


    @Reference
    private JobManager jobManager;

    public void triggerJob(final PersistableScript persistableScript, final ScriptContext context){

    }
}
