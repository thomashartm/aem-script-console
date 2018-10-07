package biz.netcentric.nclabs.groovyconsole.groovy.extension.customizer.impl;

import biz.netcentric.nclabs.groovyconsole.groovy.extension.customizer.ImportCustomizationProvider;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.osgi.service.component.annotations.Component;

/**
 * Provides default imports that are added as compiler configurations
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Component(service = ImportCustomizationProvider.class)
public class ImportCustomizationProviderImpl implements ImportCustomizationProvider {

    private static final String[] DEFAULT_STAR_IMPORTS = new String[] { "javax.jcr", "org.apache.sling.api",
            "org.apache.sling.api.resource", "com.day.cq.search", "com.day.cq.wcm.api", "com.day.cq.replication" };

    public ImportCustomizer createImportCustomizer() {
        final ImportCustomizer importCustomizer = new ImportCustomizer();

        importCustomizer.addStarImports(DEFAULT_STAR_IMPORTS);
        return importCustomizer;
    }
}
