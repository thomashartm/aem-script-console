package biz.netcentric.nclabs.groovyconsole;

import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import biz.netcentric.nclabs.groovyconsole.model.SaveResponse;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptService {

    /**
     *
     * @param groovyScript
     * @param context
     * @return
     */
    ScriptResponse runScript(final GroovyScript groovyScript, final ScriptExecutionContext context);

    /**
     * Saves a script to the jcr repository.
     *
     * @param groovyScript
     * @param context
     * @return
     */
    SaveResponse saveScript(final GroovyScript groovyScript, final ScriptExecutionContext context);
}
