package biz.netcentric.nclabs.groovyconsole.model;

import biz.netcentric.nclabs.groovyconsole.exceptions.NonExistingScriptException;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingIOException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * Model representing a rendition of a script intended to be used inside of a data view.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ScriptItem implements GroovyScriptContainer {

    private static final String NO_SCRIPT_FOUND = "No script found";

    private static final String PN_USER = "user";

    @SlingObject
    private Resource resource;

    @SlingObject
    private SlingHttpServletRequest request;

    private Optional<Resource> scriptContainer;

    private Optional<Resource> scriptFileResource;

    @PostConstruct
    public void initScriptResource() {

        this.scriptContainer = this.getScriptContainer();

        if (this.scriptContainer.isPresent()) {
            final Iterator<Resource> children = scriptContainer.get().listChildren();
            final Spliterator<Resource> splitarator = Spliterators.spliteratorUnknownSize(children, Spliterator.DISTINCT);

            this.scriptFileResource = StreamSupport.stream(splitarator, false).filter(resource -> StringUtils.equals(
                    (String) resource.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE), JcrConstants.NT_FILE)).findFirst();
        } else {
            this.scriptFileResource = Optional.empty();
        }
    }

    public String getRunAsUser() {
        if (this.scriptContainer.isPresent() && this.request != null) {
            final ValueMap data = this.scriptContainer.get().getValueMap();
            final Principal principal = this.request.getUserPrincipal();

            final String user = data.get(PN_USER, StringUtils.EMPTY);

            return StringUtils.isNotEmpty(user) ? user : principal.getName();
        }

        throw new NonExistingScriptException("Unable to find script resource ");
    }

    public String getScriptLocation() {
        return scriptFileResource.isPresent() ? scriptFileResource.get().getPath() : StringUtils.EMPTY;
    }

    public String getScriptSource() {
        if (scriptFileResource.isPresent()) {
            final Resource dataResource = scriptFileResource.get().getChild(JcrConstants.JCR_CONTENT);

            if (dataResource != null) {
                try (InputStream inputStream = dataResource.adaptTo(InputStream.class)) {
                    return IOUtils.toString(inputStream, Charset.forName(CharEncoding.UTF_8));
                } catch (IOException e) {
                    throw new SlingIOException(e);
                }

            }
        }

        throw new NonExistingScriptException("Unable to find script resource ");
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public SlingHttpServletRequest getRequest() {
        return this.request;
    }

}
