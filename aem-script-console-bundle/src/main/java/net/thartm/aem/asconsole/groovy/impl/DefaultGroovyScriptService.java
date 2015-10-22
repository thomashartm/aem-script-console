package net.thartm.aem.asconsole.groovy.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.thartm.aem.asconsole.groovy.ScriptService;
import net.thartm.aem.asconsole.script.SaveResponse;
import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */

@Service
@Component(metatype = false)
public class DefaultGroovyScriptService implements ScriptService {

    private final Logger LOG = LoggerFactory.getLogger(DefaultGroovyScriptService.class);

    @Override
    public ScriptResponse runScript(final Script script, final ScriptContext context) {

        final GroovyScriptExecution scriptResponse = new GroovyScriptExecution(script);
        scriptResponse.setStartTime(Calendar.getInstance().getTime());

        final OutputCollector outputCollector = new OutputCollector();
        final SystemOutputInterceptor outputInterceptor = new SystemOutputInterceptor(outputCollector);

        outputInterceptor.start();

        try {
            final String result = evaluateScript(script);
            scriptResponse.setResult(result);
            LOG.trace("eval() result: " + result);
        } catch (Throwable t) {
            LOG.error(t.getMessage() + t, t);
            scriptResponse.setError(t.getMessage());
        } finally {
            outputInterceptor.stop();
        }

        scriptResponse.setEndTime(Calendar.getInstance().getTime());
        scriptResponse.setOutput(outputCollector.getOutput());
        return scriptResponse;
    }

    @Override public SaveResponse saveScript(final Script script, final ScriptContext context) {
        return null;
    }

    private String evaluateScript(final Script script) {
        final Map bindingValues = new HashMap();
        final GroovyShell shell = createShell(bindingValues);
        final Object result = shell.evaluate(script.getScriptContent());

        return result != null ? result.toString() : "null";
    }

    private GroovyShell createShell(Map<String, Object> bindingValues) {
        bindingValues.put("LOG", LOG);
        return new GroovyShell(new Binding(bindingValues));
    }
}
