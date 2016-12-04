package biz.netcentric.aem.scriptconsole.job.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import biz.netcentric.aem.scriptconsole.CustomScript;
import biz.netcentric.aem.scriptconsole.ScriptContext;
import biz.netcentric.aem.scriptconsole.job.ScriptedJobService;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service
@Component(metatype = false)
public class ScriptedJobServiceImpl implements ScriptedJobService{

    public void triggerJob(final CustomScript customScript, final ScriptContext context){

    }
}
