package biz.netcentric.aem.scriptconsole.empty;

import org.apache.commons.lang.StringUtils;

import biz.netcentric.aem.scriptconsole.PersistableScript;
import biz.netcentric.aem.scriptconsole.ScriptResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class EmptyScriptResponse implements ScriptResponse {
    @Override
    public String getResult() {
        return StringUtils.EMPTY;
    }

    public PersistableScript getPersistableScript() {
        return new NonExistingPersistableScript();
    }

    @Override
    public String getOutput() {
        return StringUtils.EMPTY;
    }

    @Override
    public boolean containsError() {
        return true;
    }

    @Override
    public String getError() {
        return "Script does not exist.";
    }

    @Override
    public long getExecutionTime() {
        return 0;
    }
}