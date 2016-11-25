package biz.netcentric.aem.scriptconsole.groovy.extension.customizer;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
public interface ImportCustomizationProvider {

    /**
     * Creates import customizers to get default imports for script executions.
     * 
     * @return ImportCustomizer
     */
    ImportCustomizer createImportCustomizer();
}
