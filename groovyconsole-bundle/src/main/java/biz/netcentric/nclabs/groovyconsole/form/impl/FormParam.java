package biz.netcentric.nclabs.groovyconsole.form.impl;

/**
 * From generation params
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public enum FormParam {

    PATH("path"), NAME("name"), TYPE("fieldType"), MANDATORY("isMandatory");

    private final String name;

    FormParam(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
