package biz.netcentric.nclabs.groovyconsole.groovy;

import com.day.crx.JcrConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;

import javax.jcr.*;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A groovy script which can be executed, saved, opened.
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public interface GroovyScript {

    String DEFAULT_TEMPLATE = "nclabs/groovyconsole/templates/script";

    String DEFAULT_RESOURCE_TYPE = "nclabs/groovyconsole/templates/script";

    String NAME_PATTERN = "%s.%s";

    String FILE_EXT = "groovy";

    /**
     * Get the script content
     *
     * @return String script content
     */
    String getSourceCode();

    /**
     * The file extension represneting the script type
     *
     * @return String file extension e.g. .groovy or .js
     */
    String getFileExtension();

    /**
     * Name of the script. Must be persistable in jcr.
     *
     * @return String name of the script
     */
    String getName();

    /**
     * Path of the script.
     *
     * @return String path of the script
     */
    String getPath();

    /**
     * User for executing the script in the runner console.
     * Some scripts may be executed using a service account which can be referenced here.
     *
     * @return User in which context the script get's executed.
     */
    String getExecutionUser();

    /**
     * Title of the script
     *
     * @return Display title of the script
     */
    String getTitle();

    /**
     * The description of this script container
     *
     * @return Descriptive text
     */
    String getDescription();

    /**
     * Does the actual save operation for groovy scripts.
     *
     * @param parent Resource which acts as a folder or container for the script
     * @param name   Name of the new script
     * @return Path to the new script.
     * @throws PersistenceException
     * @throws RepositoryException
     */
    default String save(final Resource parent, final String name) throws PersistenceException, RepositoryException {
        final String nodeName = String.format(name);

        // TODO check wether everything exists and persist there
        // if not create it.
        final Resource page = getOrCreatePage(parent, nodeName);
        final Resource scriptContainer = getOrCreateNtFileForScript(page, nodeName);

        saveScript(scriptContainer);

        return page.getPath();
    }

    default Resource getOrCreatePage(final Resource parent, final String nodeName) throws PersistenceException {
        Resource page = parent.getResourceResolver().getResource(parent, nodeName);
        if (page == null) {
            final Map<String, Object> containerProperties = new HashMap<>();
            containerProperties.put(JcrConstants.JCR_PRIMARYTYPE, "cq:Page");

            page = parent.getResourceResolver().create(parent, nodeName, containerProperties);
            createJcrContentForPage(page);

            return page;
        }
        return page;
    }

    default Resource getOrCreateNtFileForScript(final Resource page, final String nodeName) throws PersistenceException {
        Resource container = page.getResourceResolver().getResource(page, nodeName);

        if (container == null) {
            final Map<String, Object> fileProperties = new HashMap<>();
            fileProperties.put(JcrConstants.JCR_PRIMARYTYPE, "nt:file");

            return page.getResourceResolver()
                    .create(page, String.format(NAME_PATTERN, nodeName, getFileExtension()), fileProperties);
        }

        return container;
    }

    default Resource createJcrContentForPage(final Resource page) throws PersistenceException {
        final Map<String, Object> jcrContentProperties = new HashMap<>();
        jcrContentProperties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        final String user = StringUtils.isNotBlank(getExecutionUser()) ? getExecutionUser() : StringUtils.EMPTY;
        jcrContentProperties.put("user", user);
        jcrContentProperties.put("cq:template", DEFAULT_TEMPLATE);
        jcrContentProperties.put("sling:resourceType", DEFAULT_RESOURCE_TYPE);

        return page.getResourceResolver().create(page, JcrConstants.JCR_CONTENT, jcrContentProperties);

    }

    default Resource saveScript(final Resource scriptContainer)
            throws RepositoryException {
        final Session session = scriptContainer.getResourceResolver().adaptTo(Session.class);
        final Node parentNode = session.getNode(scriptContainer.getPath());  //Get the client lib node in which to write the posted file

        Node fileNode = null;
        if (parentNode.hasNode(JcrConstants.JCR_CONTENT)) {
            fileNode = parentNode.getNode(JcrConstants.JCR_CONTENT);
        } else {
            fileNode = parentNode.addNode(JcrConstants.JCR_CONTENT, "nt:file");
            fileNode.addMixin("mix:referenceable");
        }

        Node resNode = null;
        if (fileNode.hasNode(JcrConstants.JCR_CONTENT)) {
            resNode = fileNode.getNode(JcrConstants.JCR_CONTENT);
        } else {
            resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
        }

        saveSourceToJcrData(session, resNode);

        session.save();

        return scriptContainer.getResourceResolver().getResource(fileNode.getPath());
    }

    default void saveSourceToJcrData(final Session session, final Node resNode) throws RepositoryException {
        resNode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");

        final ValueFactory valueFactory = session.getValueFactory();
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(getSourceCode().getBytes());
        final Binary contentValue = valueFactory.createBinary(inputStream);
        resNode.setProperty(JcrConstants.JCR_DATA, contentValue);

        final Calendar lastModified = Calendar.getInstance();
        lastModified.setTimeInMillis(lastModified.getTimeInMillis());
        resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
    }

}
