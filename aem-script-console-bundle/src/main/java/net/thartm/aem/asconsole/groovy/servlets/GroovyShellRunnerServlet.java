package net.thartm.aem.asconsole.groovy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Reference;
import net.thartm.aem.asconsole.groovy.GroovyScript;
import net.thartm.aem.asconsole.groovy.GroovyScriptContext;
import net.thartm.aem.asconsole.groovy.GroovyShellRunnerService;
import net.thartm.aem.asconsole.script.ScriptContext;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * Shell runner servlet that executes the content property of an incoming JSON post request. <br />
 * Any shell execution is bound the user's actual authorization level.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
@SlingServlet(paths = { "/bin/asconsole/groovy/post" }, methods = { "POST" }, extensions = { "json" })
public class GroovyShellRunnerServlet extends AbstractJsonPostHandlerServlet {

    @Reference
    private GroovyShellRunnerService groovyShellRunnerService;

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        final GroovyScript groovyScript = mapBodyToModel(request, mapper, GroovyScript.class);
        final ScriptContext context = new GroovyScriptContext(request);

        groovyShellRunnerService.runScript(groovyScript, context);
    }
}
