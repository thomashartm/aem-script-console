package biz.netcentric.nclabs.groovyconsole.groovy.impl.extension.closure;

/**
 * Runtime wrapper exception ot treat checked exceptions within closures.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class ClosureExecutionRuntimeException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message String The message
     */
    public ClosureExecutionRuntimeException(final String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message String The message
     * @param cause Throwable The Throwable
     */
    public ClosureExecutionRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * 
     * @param cause Throwable The Throwable
     * @param message String message pattern to format with args
     * @param args String arguments for the message
     */
    public ClosureExecutionRuntimeException(final Throwable cause, final String message, final String... args) {
        super(String.format(message, args), cause);
    }
}
