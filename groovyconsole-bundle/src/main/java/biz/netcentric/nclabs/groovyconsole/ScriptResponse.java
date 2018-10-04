package biz.netcentric.nclabs.groovyconsole;

import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;

/**
 * Execution response of a script, which is supposed to be rendered by the script backend endpoint of our user interface.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptResponse {

    String getResult();

    GroovyScript getGroovyScript();

    String getOutput();

    boolean containsError();

    String getError();

    long getExecutionTime();
}
