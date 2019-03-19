package biz.netcentric.nclabs.groovyconsole.empty;

import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import org.apache.commons.lang.StringUtils;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
public class NonExistingGroovyScript implements GroovyScript {

    @Override
    public String getSourceCode() {
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

    @Override
    public String getExecutionUser() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getTitle() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getDescription() {
        return StringUtils.EMPTY;
    }
}
