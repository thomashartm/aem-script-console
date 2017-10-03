package biz.netcentric.aem.scriptconsole.empty;

import biz.netcentric.aem.scriptconsole.CustomScript;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;


/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class NonExistingPersistableScript implements CustomScript {

    @Override
    public String getSourceCode() {
        return StringUtils.EMPTY;
    }

    public String save(final ResourceResolver resolver, final String location, final String name) throws PersistenceException {
        return StringUtils.EMPTY;
    }

    @Override
    public String getFileExtension() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getName() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getPath() {
        return StringUtils.EMPTY;
    }
}
