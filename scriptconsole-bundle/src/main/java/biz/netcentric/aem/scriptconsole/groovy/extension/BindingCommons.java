package biz.netcentric.aem.scriptconsole.groovy.extension;

import javax.jcr.Session;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;

import biz.netcentric.aem.scriptconsole.util.Assert;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.PageManager;

/**
 * Commonly used object for all bindings such as resourceresolver, sessions .... <br>
 * We share this objects in the context of all bindings and closures as each would had to create everything again and again <br/>
 * This object must not be shared among threads as it reflects session state related information
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class BindingCommons {

    private SlingHttpServletRequest request;

    private ResourceResolver resourceResolver;

    private Session session;

    private JackrabbitSession jackrabbitSession;

    private PageManager pageManager;

    private QueryBuilder queryBuilder;

    private BundleContext bundleContext;

    public SlingHttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(final SlingHttpServletRequest request) {
        Assert.notNull(request);
        this.request = request;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public void setResourceResolver(final ResourceResolver resourceResolver) {
        Assert.notNull(resourceResolver);
        this.resourceResolver = resourceResolver;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(final Session session) {
        Assert.notNull(session);
        this.session = session;
        if (session instanceof JackrabbitSession) {
            this.jackrabbitSession = (JackrabbitSession) session;
            Assert.notNull(this.jackrabbitSession);
        }
    }

    public JackrabbitSession getJackrabbitSession() {
        return jackrabbitSession;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public void setPageManager(final PageManager pageManager) {
        Assert.notNull(pageManager);
        this.pageManager = pageManager;
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(final QueryBuilder queryBuilder) {
        Assert.notNull(queryBuilder);
        this.queryBuilder = queryBuilder;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(final BundleContext bundleContext) {
        Assert.notNull(bundleContext);
        this.bundleContext = bundleContext;
    }
}
