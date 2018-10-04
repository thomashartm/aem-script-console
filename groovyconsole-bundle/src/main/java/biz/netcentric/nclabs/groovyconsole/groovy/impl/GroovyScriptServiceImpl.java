package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import biz.netcentric.nclabs.groovyconsole.ScriptExecutionContext;
import biz.netcentric.nclabs.groovyconsole.ScriptResponse;
import biz.netcentric.nclabs.groovyconsole.ScriptService;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.BindingExtensionsProviderService;
import biz.netcentric.nclabs.groovyconsole.groovy.extension.customizer.ImportCustomizationProvider;
import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import biz.netcentric.nclabs.groovyconsole.model.SaveResponse;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@Service
@Component(metatype = false)
public class GroovyScriptServiceImpl implements ScriptService {

    private final Logger LOG = LoggerFactory.getLogger(GroovyScriptServiceImpl.class);

    private static final String NO_RETURN_VALUE_PROVIDED = "No return value provided by script.";

    @Reference
    private BindingExtensionsProviderService bindingProviderService;

    @Reference
    private ImportCustomizationProvider importCustomizationProvider;

    @Override
    public ScriptResponse runScript(final GroovyScript groovyScript, final ScriptExecutionContext context) {

        final GroovyScriptResponse scriptResponse = new GroovyScriptResponse(groovyScript);
        scriptResponse.setStartTime(Calendar.getInstance().getTime());

        final OutputCollector outputCollector = new OutputCollector();
        final SystemOutputInterceptor outputInterceptor = new SystemOutputInterceptor(outputCollector);

        outputInterceptor.start();
        try {
            final String result = evaluateScript(groovyScript, context);
            scriptResponse.setResult(result);

            if(LOG.isTraceEnabled()) {
                LOG.trace("eval() script result: " + result);
            }
        } catch (final Exception e) {
            LOG.error(e.getMessage() + e, e);
            scriptResponse.setError(e.getMessage());
        } finally {
            outputInterceptor.stop();
            try {
                outputInterceptor.close();
            } catch (IOException e) {
                LOG.error(e.getMessage() + e, e);
            }
        }

        scriptResponse.setEndTime(Calendar.getInstance().getTime());
        scriptResponse.setOutput(outputCollector.getOutput());
        return scriptResponse;
    }

    @Override
    public SaveResponse saveScript(final GroovyScript groovyScript, final ScriptExecutionContext context) {
        throw new NoSuchMethodError("Not implemented yet");
    }

    private String evaluateScript(final GroovyScript groovyScript, final ScriptExecutionContext context) {
        final GroovyShell shell = createConfiguredShell(context);
        final Object result = shell.evaluate(groovyScript.getSourceCode());

        return result != null ? result.toString() : NO_RETURN_VALUE_PROVIDED;
    }

    private GroovyShell createConfiguredShell(final ScriptExecutionContext context) {
        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(importCustomizationProvider.createImportCustomizer());

        final Binding binding = bindingProviderService.createBindings(context);

        return new GroovyShell(binding, configuration);
    }
}
