package net.thartm.aem.asconsole.groovy.impl;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import net.thartm.aem.asconsole.extension.BindingExtensionsProviderService;
import net.thartm.aem.asconsole.extension.customizer.ImportCustomizationProvider;
import net.thartm.aem.asconsole.groovy.GroovyScript;
import net.thartm.aem.asconsole.groovy.GroovyScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;
import net.thartm.aem.asconsole.util.ReflectionUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class DefaultGroovyScriptServiceTest {

    private DefaultGroovyScriptService runnerService;

    private BindingExtensionsProviderService bindingExtensionService;

    private ImportCustomizationProvider importCustomizerProvider;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        runnerService = new DefaultGroovyScriptService();
        bindingExtensionService = mock(BindingExtensionsProviderService.class);
        importCustomizerProvider = mock(ImportCustomizationProvider.class);
        ReflectionUtil.setFieldValue(this.runnerService, "bindingProviderService", bindingExtensionService);
        ReflectionUtil.setFieldValue(this.runnerService, "importCustomizationProvider", importCustomizerProvider);
    }

    @Test
    public void testRunScript() throws Exception {

        final Binding binding = mock(Binding.class);
        when(this.bindingExtensionService.getExtensionsBinding(any(SlingHttpServletRequest.class))).thenReturn(binding);
        when(this.importCustomizerProvider.createImportCustomizer()).thenReturn(new ImportCustomizer());

        final ScriptResponse response = executeScript("println 'test'");
        Assert.assertEquals("test", response.getOutput());
        Assert.assertFalse("test", response.containsError());
    }

    @Test
    public void testBindingIntegration() throws Exception {

        final Map<String, Object> mapping = Maps.newHashMap();
        mapping.put("printer", new BindablePrinter());

        final Binding binding = new Binding(mapping);
        when(this.bindingExtensionService.getExtensionsBinding(any(SlingHttpServletRequest.class))).thenReturn(binding);
        when(this.importCustomizerProvider.createImportCustomizer()).thenReturn(new ImportCustomizer());

        final ScriptResponse response = executeScript("printer.print('test')");
        Assert.assertEquals("test", response.getResult());
        Assert.assertFalse("test", response.containsError());
    }

    static class BindablePrinter {

        public String print(final String value) {
            return value;
        }
    }

    private ScriptResponse executeScript(final String scriptContent) {
        final GroovyScript script = new GroovyScript(scriptContent);
        final GroovyScriptContext scriptContext = new GroovyScriptContext(mock(SlingHttpServletRequest.class));
        return this.runnerService.runScript(script, scriptContext);
    }

}