package biz.netcentric.aem.scriptconsole.models;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2016
 */
public enum Icon {
    FOLDER("folder"), FILE("file");

    private String name;

    Icon(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
