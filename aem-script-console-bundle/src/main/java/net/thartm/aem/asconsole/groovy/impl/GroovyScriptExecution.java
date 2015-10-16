package net.thartm.aem.asconsole.groovy.impl;

import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptResponse;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptExecution implements ScriptResponse {

    private Script script;

    private String result;

    private String output;

    private Date startTime;

    private Date endTime;

    private String error;

    GroovyScriptExecution(final Script script) {
        this.script = script;
    }

    public String getResult() {
        return result;
    }

    public Script getScript() {
        return script;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public boolean containsError() {
        return StringUtils.isNotBlank(error);
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setScript(final Script script) {
        this.script = script;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public void setOutput(final String output) {
        this.output = output;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}
