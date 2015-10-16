package net.thartm.aem.asconsole.groovy.impl;

import net.thartm.aem.asconsole.groovy.GroovyScript;
import net.thartm.aem.asconsole.groovy.GroovyScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;
import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class DefaultGroovyScriptServiceTest {

    private DefaultGroovyScriptService runnerService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        this.runnerService = new DefaultGroovyScriptService();
    }

    @Test
    public void testRunScript() throws Exception {
        final GroovyScript script = new GroovyScript("println 'test'");
        final GroovyScriptContext scriptContext = new GroovyScriptContext(mock(SlingHttpServletRequest.class));

        final ScriptResponse response = this.runnerService.runScript(script, scriptContext);

        Assert.assertEquals("test", response.getOutput());
        Assert.assertFalse("test", response.containsError());
    }

}