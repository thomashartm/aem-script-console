package biz.netcentric.nclabs.groovyconsole;

import biz.netcentric.nclabs.groovyconsole.model.PersistableScript;
import biz.netcentric.nclabs.groovyconsole.model.SaveResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptService {

    /**
     *
     * @param persistableScript
     * @param context
     * @return
     */
    ScriptResponse runScript(final PersistableScript persistableScript, final ScriptExecutionContext context);

    /**
     * Saves a script to the jcr repository.
     *
     * @param persistableScript
     * @param context
     * @return
     */
    SaveResponse saveScript(final PersistableScript persistableScript, final ScriptExecutionContext context);
}
