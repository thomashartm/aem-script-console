package biz.netcentric.nclabs.groovyconsole.servlets;

/**
 * Default parameters for script related requests.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public enum DefaultParameter {

    SCRIPT("script"),
    SCRIPT_LOCATION("location");

    private final String param;

    DefaultParameter(final String param) {
        this.param = param;
    }

    /**
     * Provides the param.
     * @return String of the param.
     */
    public String get() {
        return this.param;
    }
}
