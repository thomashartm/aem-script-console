package net.thartm.aem.asconsole.extension.customizer;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.util.Set;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
public interface ImportCustomizationProvider {

    ImportCustomizer createImportCustomizer();
}
