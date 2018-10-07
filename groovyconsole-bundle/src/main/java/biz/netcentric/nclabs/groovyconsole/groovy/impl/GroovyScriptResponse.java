package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import biz.netcentric.nclabs.groovyconsole.ScriptResponse;
import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Response and output of an executed groovy script.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class GroovyScriptResponse implements ScriptResponse {

    private GroovyScript groovyScript;

    private String result;

    private String output;

    private Date startTime;

    private Date endTime;

    private String error;

    private boolean failed = false;

    private long executionTime = 0;

    GroovyScriptResponse(final GroovyScript groovyScript) {
        this.groovyScript = groovyScript;
    }

    public String getResult() {
        return result;
    }

    public GroovyScript getGroovyScript() {
        return groovyScript;
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

    public void setGroovyScript(final GroovyScript groovyScript) {
        this.groovyScript = groovyScript;
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
