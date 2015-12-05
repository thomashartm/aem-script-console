package net.thartm.aem.asconsole.groovy.extension.customizer;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
public interface ImportCustomizationProvider {

    ImportCustomizer createImportCustomizer();
}
