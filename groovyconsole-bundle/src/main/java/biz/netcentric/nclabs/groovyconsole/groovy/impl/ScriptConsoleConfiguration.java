package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OSGi configuration for the script console
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service(value = ScriptConsoleConfiguration.class)
@Component(label = "NCLabs Groovyconsole Configuration", description = "Used for configuration of the eMB script console", specVersion = "1.1", metatype = true)
public class ScriptConsoleConfiguration {

    private static final String SEMICOLON = ";";

    private static final Logger LOG = LoggerFactory.getLogger(ScriptConsoleConfiguration.class);

    private String[] ADMIN_GROUPS = new String[] {
            "administrators"
    };

    private String[] USERS_WHITELIST = new String[] {};

    private static final String ALLOWED_GROUPS = "groups.allowed";
    @Property(name = ALLOWED_GROUPS, value = StringUtils.EMPTY, label = "Allowed groups.", description = "Group allowance configuration. The listed groups are allowed to execute scripts. Separate groups by semicolon. The administrators group is set by default.")
    private Set<String> allowedGroups = new CopyOnWriteArraySet();

    private static final String ALLOWED_USERS = "users.allowed";
    @Property(name = ALLOWED_USERS, value = StringUtils.EMPTY, label = "Allowed users.", description = "User allowance configuration. Separate users by semicolon.")
    private Set<String> allowedUsers = new CopyOnWriteArraySet();

    @Activate
    public void activate(final ComponentContext context) {
        final Dictionary<?, ?> properties = context.getProperties();
        final String allowedGroups = PropertiesUtil.toString(properties.get(ALLOWED_GROUPS), StringUtils.EMPTY);
        final String allowedUsers = PropertiesUtil.toString(properties.get(ALLOWED_USERS), StringUtils.EMPTY);

        final Set<String> groups = getAllowedUsersOrGroups(allowedGroups);
        groups.addAll(Arrays.asList(ADMIN_GROUPS));

        final Set<String> users = getAllowedUsersOrGroups(allowedUsers);
        users.addAll(Arrays.asList(USERS_WHITELIST));

        if (LOG.isTraceEnabled()) {
            LOG.trace("Allowed groups [{}]", StringUtils.join(groups, SEMICOLON));
            LOG.trace("Allowed users [{}]", StringUtils.join(users, SEMICOLON));
        }

        this.allowedGroups.addAll(groups);
        this.allowedUsers.addAll(users);
    }

    private Set<String> getAllowedUsersOrGroups(final String allowedItems) {
        final String[] splittedItems = StringUtils.split(allowedItems, SEMICOLON);
        return Stream.of(splittedItems).filter(i -> StringUtils.isNotBlank(i)).collect(Collectors.toSet());
    }

    public Set<String> getAllowedGroups() {
        return this.allowedGroups;
    }

    public Set<String> getAllowedUsers() {
        return this.allowedUsers;
    }
}
