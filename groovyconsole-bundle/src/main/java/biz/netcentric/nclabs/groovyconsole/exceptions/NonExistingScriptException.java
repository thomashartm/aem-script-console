package biz.netcentric.nclabs.groovyconsole.exceptions;

/**
 * Supposed to be thrown whenever a non existing script is called.
 * Non existing script can be a script resource without a script node, property or reference or an illegal path pointed to the shell.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public class NonExistingScriptException extends RuntimeException {

    public NonExistingScriptException(final String message) {
        super(message);
    }
}
