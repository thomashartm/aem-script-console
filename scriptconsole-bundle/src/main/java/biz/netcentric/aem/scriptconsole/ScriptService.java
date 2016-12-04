package biz.netcentric.aem.scriptconsole;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface ScriptService {

    /**
     *
     * @param customScript
     * @param context
     * @return
     */
    ScriptResponse runScript(final CustomScript customScript, final ScriptContext context);

    /**
     * Saves a script to the jcr repository.
     *
     * @param customScript
     * @param context
     * @return
     */
    SaveResponse saveScript(final CustomScript customScript, final ScriptContext context);
}
