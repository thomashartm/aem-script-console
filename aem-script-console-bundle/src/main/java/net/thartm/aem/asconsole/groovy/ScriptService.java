package net.thartm.aem.asconsole.groovy;

import net.thartm.aem.asconsole.script.SaveResponse;
import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptService {

    ScriptResponse runScript(Script script, ScriptContext context);

    /**
     * Saves a script to the jcr repository.
     *
     * @param script
     * @param context
     * @return
     */
    SaveResponse saveScript(Script script, ScriptContext context);
}
