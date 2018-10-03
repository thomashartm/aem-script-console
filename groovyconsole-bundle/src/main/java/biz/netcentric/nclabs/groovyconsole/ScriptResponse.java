package biz.netcentric.nclabs.groovyconsole;

import biz.netcentric.nclabs.groovyconsole.model.PersistableScript;

/**
 * Execution response of a script, which is supposed to be rendered by the script backend endpoint of our user interface.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptResponse {

    String getResult();

    PersistableScript getPersistableScript();

    String getOutput();

    boolean containsError();

    String getError();

    long getExecutionTime();
}
