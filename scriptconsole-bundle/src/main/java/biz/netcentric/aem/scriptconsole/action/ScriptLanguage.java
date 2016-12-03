package biz.netcentric.aem.scriptconsole.action;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
public enum ScriptLanguage {

    GROOVY("Groovy", "groovy"), JAVASCRIPT("Javascript", "js");

    private String name;
    private String extension;

    ScriptLanguage(final String name, final String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
