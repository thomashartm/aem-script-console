package biz.netcentric.nclabs.groovyconsole.servlets;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestParameterMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles request parameters for implementing consumers.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public interface WithRequestParameters {

    default List<String> getParameterValues(final SlingHttpServletRequest request, final String... paramNames) {
        final List<String> allParameters = new ArrayList<>();

        for (final String paramName : paramNames) {
            final RequestParameter[] parameters = request.getRequestParameterMap().getValues(paramName);
            if (parameters != null) {
                Lists.newArrayList(parameters).stream().forEach(p -> allParameters.add(p.getString()));
            }
        }

        return allParameters;
    }

    default <T extends Object> T getParameterValue(final SlingHttpServletRequest request, final String paramName, T defaultValue) {
        final String value = request.getParameter(paramName);
        if (StringUtils.isNotEmpty(value)) {
            if (defaultValue instanceof Boolean) {
                return (T) Boolean.valueOf(value);
            }

            if (StringUtils.isNumeric(value) && defaultValue instanceof Integer) {
                return (T) Integer.valueOf(value);
            }
            return (T) defaultValue.getClass().cast(value);
        }

        return defaultValue;
    }

    default <T extends Object> T getMandatoryParameterValue(final SlingHttpServletRequest request, final String paramName, Class<T> clazz) {
        final String value = request.getParameter(paramName);
        if (StringUtils.isNotEmpty(value)) {
            if (clazz == Boolean.class) {
                return (T) Boolean.valueOf(value);
            }

            if (StringUtils.isNumeric(value) && clazz == Integer.class) {
                return (T) Integer.valueOf(value);
            }
            return (T) clazz.cast(value);
        }

        throw new IllegalArgumentException("The parameter " + paramName + " is a mandatory parameter.");
    }

    default boolean allParametersPresent(final SlingHttpServletRequest request, final String... expectedParameters) {
        final RequestParameterMap parameterMap = request.getRequestParameterMap();
        return Arrays.asList(expectedParameters)
                .stream()
                .allMatch(param -> parameterMap.containsKey(param));
    }

    default boolean allParameterHaveSameLength(final SlingHttpServletRequest request, final String... expectedParameters) {
        if (expectedParameters == null || expectedParameters.length == 1) {
            return true;
        }

        final RequestParameterMap parameterMap = request.getRequestParameterMap();
        int referenceLength = parameterMap.getValues(expectedParameters[0]).length;

        return Arrays.asList(expectedParameters).stream().allMatch(param -> {
                    RequestParameter[] expectedParamEntry = parameterMap.getValues(param);
                    return expectedParamEntry != null && referenceLength == expectedParamEntry.length;
                }
        );
    }
}