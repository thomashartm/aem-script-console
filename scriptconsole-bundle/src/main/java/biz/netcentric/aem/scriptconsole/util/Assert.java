package biz.netcentric.aem.scriptconsole.util;

import org.apache.commons.lang.StringUtils;

/**
 * Asserts for arguments to enforce non null or non empty arguments.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class Assert {

    private Assert() {
    }

    public static final void notNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static final void notNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null.");
        }
    }

    public static final void notBlank(final String argument, final String message) {
        if (StringUtils.isBlank(argument)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static final void notBlank(final String argument) {
        if (StringUtils.isBlank(argument)) {
            throw new IllegalArgumentException("Argument should not be null or empty.");
        }
    }

    public static void isTrue(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("Argument should be true.");
        }
    }

    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
