package biz.netcentric.aem.scriptconsole.empty;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

import biz.netcentric.aem.scriptconsole.PersistableScript;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class NonExistingPersistableScript implements PersistableScript {

    @Override
    public String getSourceCode() {
        return StringUtils.EMPTY;
    }

    @Override
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
