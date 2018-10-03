package biz.netcentric.nclabs.groovyconsole.exceptions;

/**
 * TODO - add javadoc
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public class NonExistingScriptException extends RuntimeException {

    public NonExistingScriptException(final String message){
        super(message);
    }
}
