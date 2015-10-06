package net.thartm.aem.asconsole.groovy.impl;

import net.thartm.aem.asconsole.script.ScriptResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptResponse implements ScriptResponse {

    private final String result;

    private final String script;

    GroovyScriptResponse(final String script, final Object result) {
        this.script = script;

        this.result = (String) result;
    }

    public String getResult() {
        return result;
    }

    public String getScript() {
        return script;
    }
}
