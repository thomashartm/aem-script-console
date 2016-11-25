package biz.netcentric.aem.scriptconsole;

/**
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
