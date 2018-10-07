package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Dynamically submitted groovy script which is currently not started by submitting the path but the actual source code.
 * Dynamic groovy script can be saved and then be loaded as {@link PersistedGroovyScript}
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public class DynamicGroovyScript implements GroovyScript {

    private String sourceCode;

    private String fileExtension;

    private String name;

    private String path;

    public DynamicGroovyScript(final String sourceCode, final String fileExtension) {
        this.sourceCode = sourceCode;
        this.fileExtension = fileExtension;

        // init as defaults. This may get set when the script is going to be stored
        this.name = StringUtils.EMPTY;
        this.path = StringUtils.EMPTY;
    }

    @Override
    public String getSourceCode() {
        return this.sourceCode;
    }

    @Override
    public String save(ResourceResolver resolver, String location, String name) throws PersistenceException {
        throw new NoSuchMethodError("Not implemented yet");
    }

    @Override
    public String getFileExtension() {
        return this.fileExtension;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    /**
     *
     * @param sourceCode
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    /**
     *
     * @param fileExtension
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
