package biz.netcentric.aem.scriptconsole.groovy.impl;

import java.util.Calendar;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.aem.scriptconsole.CustomScript;
import biz.netcentric.aem.scriptconsole.SaveResponse;
import biz.netcentric.aem.scriptconsole.ScriptContext;
import biz.netcentric.aem.scriptconsole.ScriptResponse;
import biz.netcentric.aem.scriptconsole.ScriptService;
import biz.netcentric.aem.scriptconsole.groovy.extension.BindingExtensionsProviderService;
import biz.netcentric.aem.scriptconsole.groovy.extension.customizer.ImportCustomizationProvider;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */

@Service
@Component(metatype = false)
public class GroovyScriptServiceImpl implements ScriptService {

    private final Logger LOG = LoggerFactory.getLogger(GroovyScriptServiceImpl.class);

    @Reference
    private BindingExtensionsProviderService bindingProviderService;

    @Reference
    private ImportCustomizationProvider importCustomizationProvider;

    @Override
    public ScriptResponse runScript(final CustomScript customScript, final ScriptContext context) {

        final GroovyScriptResponse scriptResponse = new GroovyScriptResponse(customScript);
        scriptResponse.setStartTime(Calendar.getInstance().getTime());

        final OutputCollector outputCollector = new OutputCollector();
        final SystemOutputInterceptor outputInterceptor = new SystemOutputInterceptor(outputCollector);

        outputInterceptor.start();
        try {
            final String result = evaluateScript(customScript, context);
            scriptResponse.setResult(result);
            LOG.trace("eval() script result: " + result);
        } catch (final Throwable t) {
            LOG.error(t.getMessage() + t, t);
            scriptResponse.setError(t.getMessage());
        } finally {
            outputInterceptor.stop();
        }

        scriptResponse.setEndTime(Calendar.getInstance().getTime());
        scriptResponse.setOutput(outputCollector.getOutput());
        return scriptResponse;
    }

    @Override
    public SaveResponse saveScript(final CustomScript customScript, final ScriptContext context) {
        // TODO implement save operation
        return null;
    }

    private String evaluateScript(final CustomScript customScript, final ScriptContext context) {
        final GroovyShell shell = createConfiguredShell(context);
        final Object result = shell.evaluate(customScript.getSourceCode());

        return result != null ? result.toString() : "null";
    }

    private GroovyShell createConfiguredShell(final ScriptContext context) {
        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(importCustomizationProvider.createImportCustomizer());

        final Binding binding = bindingProviderService.createBindings(context.getCurrentRequest());

        return new GroovyShell(binding, configuration);
    }
}
