package biz.netcentric.aem.scriptconsole;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
public interface PersistenceService {

    void createScriptContainer(String location, String name, String extension);

    void updateScriptContainer(String location, String name, String extension, String scriptContent);
}
