package biz.netcentric.aem.scriptconsole;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
public interface ScriptPersistenceService {

    void createScriptContainer(final String location, final String name, final String extension);
}
