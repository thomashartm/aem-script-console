package net.thartm.aem.asconsole.groovy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import net.thartm.aem.asconsole.groovy.GroovyScript;
import net.thartm.aem.asconsole.groovy.GroovyScriptContext;
import net.thartm.aem.asconsole.groovy.ScriptService;
import net.thartm.aem.asconsole.script.ScriptContext;
import net.thartm.aem.asconsole.script.ScriptResponse;
import net.thartm.aem.asconsole.script.servlets.AbstractJsonPostHandlerServlet;
import org.apache.felix.scr.annotations.Reference;
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
public class GroovyScriptServlet extends AbstractJsonPostHandlerServlet {

    @Reference
    private ScriptService scriptService;

    @Override
    public void handleRequest(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
            final ObjectMapper mapper) throws IOException, RepositoryException {

        final String script = request.getParameter("script");
        final GroovyScript groovyScript = new GroovyScript(script);
        final ScriptContext context = new GroovyScriptContext(request);

        final ScriptResponse scriptResponse = scriptService.runScript(groovyScript, context);
        //PrintWriter out = response.getWriter();
        //mapper.writeValue(out, scriptResponse);

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String json = ow.writeValueAsString(scriptResponse);
        response.getWriter().append(json);
        /*try {
            final String json = new JSONObject().put("JSON", "Hello, World!").toString();
            response.getWriter().append(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
