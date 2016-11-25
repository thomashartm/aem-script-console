package biz.netcentric.aem.scriptconsole.servlets;

import java.util.Collection;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public interface WithAccessCheck {

    default boolean isPriviledgedUser(final SlingHttpServletRequest request) throws RepositoryException {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        final UserManager userManager = resourceResolver.adaptTo(UserManager.class);
        if (userManager == null) {
            throw new IllegalStateException("Unable to authenticate requesting user");
        }

        final Authorizable currentAuthorizable = userManager.getAuthorizable(resourceResolver.getUserID());

        for (final String groupName : getEnabledGroups()) {
            final Authorizable authorizableGroup = userManager.getAuthorizable(groupName);
            if (authorizableGroup != null  && authorizableGroup.isGroup()) {
                final Group group = (Group) authorizableGroup;
                if (group.isMember(currentAuthorizable)) {
                    return true;
                }
            }
        }

        return false;
    }

    Collection<String> getEnabledGroups();
}
