package net.thartm.aem.asconsole.script;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptResponse {

    String getResult();

    Script getScript();

    String getOutput();

    boolean containsError();

    String getError();
}
