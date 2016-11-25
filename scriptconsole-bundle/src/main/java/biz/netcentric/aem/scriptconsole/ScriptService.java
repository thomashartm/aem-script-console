package biz.netcentric.aem.scriptconsole;

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
    ScriptResponse runScript(final PersistableScript persistableScript, final ScriptContext context);

    /**
     * Saves a script to the jcr repository.
     *
     * @param persistableScript
     * @param context
     * @return
     */
    SaveResponse saveScript(final PersistableScript persistableScript, final ScriptContext context);
}
