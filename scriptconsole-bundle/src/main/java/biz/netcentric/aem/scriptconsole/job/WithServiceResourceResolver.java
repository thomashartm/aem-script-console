package biz.netcentric.aem.scriptconsole.job;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a resource resolver.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public interface WithServiceResourceResolver {

    String SUBSERVICE_NAME = "script-console-jobs-user";

    default Map<String, Object> getAuthenticationInfo() {
        final Map<String, Object> authenticationInfo = new HashMap<String, Object>();
        authenticationInfo.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
        return authenticationInfo;
    }

    default ResourceResolver createResourceResolver() throws LoginException {
        return getResourceResolverFactory().getServiceResourceResolver(getAuthenticationInfo());
    }

    ResourceResolverFactory getResourceResolverFactory();

    default void closeSilently(final ResourceResolver resourceResolver){
       if(resourceResolver != null){
           resourceResolver.close();
       }
    }
}
