package biz.netcentric.aem.scriptconsole.groovy.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import biz.netcentric.aem.scriptconsole.CustomScript;
import biz.netcentric.aem.scriptconsole.ScriptResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptResponse implements ScriptResponse {

    private CustomScript customScript;

    private String result;

    private String output;

    private Date startTime;

    private Date endTime;

    private String error;

    private boolean failed = false;

    private long executionTime = 0;

    GroovyScriptResponse(final CustomScript customScript) {
        this.customScript = customScript;
    }

    public String getResult() {
        return result;
    }

    public CustomScript getCustomScript() {
        return customScript;
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

    public void setCustomScript(final CustomScript customScript) {
        this.customScript = customScript;
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
        if (this.startTime != null && this.endTime != null) {
            this.executionTime = this.endTime.getTime() - this.startTime.getTime();
        }
    }

    public String getError() {
        return error;
    }

    @Override
    public long getExecutionTime() {
        return this.executionTime;
    }

    public void setError(final String error) {
        this.error = error;
        this.failed = true;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(final boolean failed) {
        this.failed = failed;
    }
}
