package biz.netcentric.aem.scriptconsole;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptResponse {

    String getResult();

    CustomScript getCustomScript();

    String getOutput();

    boolean containsError();

    String getError();

    long getExecutionTime();
}
