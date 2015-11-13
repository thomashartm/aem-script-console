package net.thartm.aem.asconsole.extension.customizer.impl;

import com.google.common.collect.Sets;
import net.thartm.aem.asconsole.extension.customizer.ImportCustomizationProvider;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.util.Set;

/**
 * Provides default imports that are added as compiler configurations
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2015
 */
@Service
@Component(metatype = true)
public class ImportCustomizationProviderImpl implements ImportCustomizationProvider {

    private static final String[] DEFAULT_STAR_IMPORTS = new String[] { "javax.jcr", "org.apache.sling.api",
            "org.apache.sling.api.resource", "com.day.cq.search", "com.day.cq.wcm.api", "com.day.cq.replication" };


    public ImportCustomizer createImportCustomizer(){
        final ImportCustomizer importCustomizer = new ImportCustomizer();

        importCustomizer.addStarImports(DEFAULT_STAR_IMPORTS);
        return importCustomizer;
    }
}
