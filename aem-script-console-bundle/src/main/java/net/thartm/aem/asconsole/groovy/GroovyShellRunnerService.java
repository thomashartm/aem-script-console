package net.thartm.aem.asconsole.groovy;

import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface GroovyShellRunnerService {
    ScriptResponse runScript(Script script, ScriptContext context);
}
