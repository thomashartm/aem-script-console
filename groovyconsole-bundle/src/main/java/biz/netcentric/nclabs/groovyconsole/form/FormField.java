package biz.netcentric.nclabs.groovyconsole.form;

/**
 * Is a form field whoch is supposed to be stored in the repo and deleiverd via a groovy shell form
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public class FormField {

    private String name;

    private String type;

    private boolean isMandatory;

    public FormField(final String name, final String type) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }
}
