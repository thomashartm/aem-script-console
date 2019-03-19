package biz.netcentric.nclabs.groovyconsole.model;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Optional;

/**
 * The implementation class must be a script container which is
 * actually a resource structure providing at least a script, some metainformation and optionally a form.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public interface ScriptContainer {

    /**
     * Get's the script container's resource if it exists
     *
     * @return optional of a resource
     */
    default Optional<Resource> getScriptContainer() {
        final Resource resource = getResource();
        final ResourceResolver resolver = resource.getResourceResolver();
        final SlingHttpServletRequest request = getRequest();

        if (resolver != null && request != null && request.getRequestPathInfo() != null) {

            final String path = request.getRequestPathInfo().getSuffix();
            if (StringUtils.isNotEmpty(path)) {
                final Resource script = resolver.getResource(path);
                return Optional.ofNullable(script);
            }
        }
        return Optional.empty();
    }

    /**
     * Provides the current @{@link Resource}
     *
     * @return The resource
     */
    Resource getResource();

    /**
     * Provides the current {@link SlingHttpServletRequest}
     *
     * @returnThe request
     */
    SlingHttpServletRequest getRequest();

    /**
     * Name of the user which should execute the script. This should be either a service user named in the script container or the request's user will be taken.
     *
     * @return Username as a string
     */
    String getRunAsUser();
}
