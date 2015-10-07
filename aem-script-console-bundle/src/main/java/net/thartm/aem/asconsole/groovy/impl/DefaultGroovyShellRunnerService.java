package net.thartm.aem.asconsole.groovy.impl;

import groovy.lang.GroovyShell;
import net.thartm.aem.asconsole.groovy.GroovyShellRunnerService;
import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */

@Service
@Component(metatype = false)
public class DefaultGroovyShellRunnerService implements GroovyShellRunnerService {

    @Override
    public ScriptResponse runScript(final Script script, final ScriptContext context) {
        final GroovyShell groovyShell = new GroovyShell();

        final groovy.lang.Script groovyScript = groovyShell.parse(script.getScript());

        final Object result = groovyScript.run();

        final GroovyScriptResponse response = new GroovyScriptResponse(script.getScript(), result);

        return response;
    }
}
