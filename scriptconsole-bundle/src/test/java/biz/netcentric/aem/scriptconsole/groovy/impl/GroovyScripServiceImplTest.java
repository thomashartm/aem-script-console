package biz.netcentric.aem.scriptconsole.groovy.impl;

import biz.netcentric.aem.scriptconsole.ScriptResponse;
import com.google.common.collect.Maps;
import groovy.lang.Binding;
import biz.netcentric.aem.scriptconsole.groovy.extension.BindingExtensionsProviderService;
import biz.netcentric.aem.scriptconsole.groovy.extension.customizer.ImportCustomizationProvider;

import biz.netcentric.aem.scriptconsole.groovy.GroovyScriptContext;

import biz.netcentric.aem.scriptconsole.util.ReflectionUtil;
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
public class GroovyScripServiceImplTest {

    private GroovyScripServiceImplTest runnerService;

    private BindingExtensionsProviderService bindingExtensionService;

    private ImportCustomizationProvider importCustomizerProvider;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        /*runnerService = new GroovyScripServiceImplTest();
        bindingExtensionService = mock(BindingExtensionsProviderService.class);
        importCustomizerProvider = mock(ImportCustomizationProvider.class);
        ReflectionUtil.setFieldValue(this.runnerService, "bindingProviderService", bindingExtensionService);
        ReflectionUtil.setFieldValue(this.runnerService, "importCustomizationProvider", importCustomizerProvider);*/
    }

    @Test
    public void testRunScript() throws Exception {

        /*final Binding binding = mock(Binding.class);
        when(this.bindingExtensionService.getExtensionsBinding(any(SlingHttpServletRequest.class))).thenReturn(binding);
        when(this.importCustomizerProvider.createImportCustomizer()).thenReturn(new ImportCustomizer());

        final ScriptResponse response = executeScript("println 'test'");
        Assert.assertEquals("test", response.getOutput());
        Assert.assertFalse("test", response.containsError());*/
    }

    @Test
    public void testBindingIntegration() throws Exception {

       /* final Map<String, Object> mapping = Maps.newHashMap();
        mapping.put("printer", new BindablePrinter());

        final Binding binding = new Binding(mapping);
        when(this.bindingExtensionService.getExtensionsBinding(any(SlingHttpServletRequest.class))).thenReturn(binding);
        when(this.importCustomizerProvider.createImportCustomizer()).thenReturn(new ImportCustomizer());

        final ScriptResponse response = executeScript("printer.print('test')");
        Assert.assertEquals("test", response.getResult());
        Assert.assertFalse("test", response.containsError());*/
    }

    static class BindablePrinter {

        public String print(final String value) {
            return value;
        }
    }

    private ScriptResponse executeScript(final String scriptContent) {
       /* final GroovyScript script = new GroovyScript(scriptContent);
        final GroovyScriptContext scriptContext = new GroovyScriptContext(mock(SlingHttpServletRequest.class));
        return this.runnerService.runScript(script, scriptContext);*/
        return null;
    }

}