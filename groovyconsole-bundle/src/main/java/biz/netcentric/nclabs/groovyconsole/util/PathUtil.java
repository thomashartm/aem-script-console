package biz.netcentric.nclabs.groovyconsole.util;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Provides path related utility operations
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2016
 */
public class PathUtil {

    public static final char EXTENSION_SEPARATOR = '.';
    public static final char PATH_SEPARATOR = '/';

    public static final String pathFromSuffix(final SlingHttpServletRequest request) {

        final String suffix = request.getRequestPathInfo().getSuffix();
        if (StringUtils.isNotEmpty(suffix)) {
            return removeExtension(suffix);
        }
        return StringUtils.EMPTY;
    }

    public static String removeExtension(final String path) {
        if (StringUtils.isEmpty(path)) {
            return StringUtils.EMPTY;
        }

        final int index = indexOfExtension(path);
        return (index > -1) ? path.substring(0, index) : path;
    }

    public static int indexOfExtension(final String path) {
        if (StringUtils.isEmpty(path)) {
            return -1;
        }

        final int extensionPos = path.lastIndexOf(EXTENSION_SEPARATOR);
        final int lastSeparator = indexOfLastSeparator(path);

        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    public static int indexOfLastSeparator(final String path) {
        if (StringUtils.isEmpty(path)) {
            return -1;
        }
        return path.lastIndexOf(PATH_SEPARATOR);
    }
}
