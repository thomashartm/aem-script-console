package biz.netcentric.aem.scriptconsole.groovy.extension.customizer.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import biz.netcentric.aem.scriptconsole.groovy.extension.customizer.ImportCustomizationProvider;

/**
 * Provides default imports that are added as compiler configurations
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service
@Component(metatype = false)
public class ImportCustomizationProviderImpl implements ImportCustomizationProvider {

    private static final String[] DEFAULT_STAR_IMPORTS = new String[] { "javax.jcr", "org.apache.sling.api",
            "org.apache.sling.api.resource", "com.day.cq.search", "com.day.cq.wcm.api", "com.day.cq.replication" };

    public ImportCustomizer createImportCustomizer() {
        final ImportCustomizer importCustomizer = new ImportCustomizer();

        importCustomizer.addStarImports(DEFAULT_STAR_IMPORTS);
        return importCustomizer;
    }
}
