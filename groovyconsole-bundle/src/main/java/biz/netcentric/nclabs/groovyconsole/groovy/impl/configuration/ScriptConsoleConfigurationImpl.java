package biz.netcentric.nclabs.groovyconsole.groovy.impl.configuration;

import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(service = { ScriptConsoleConfiguration.class }, configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = UserAccessConfiguration.class)
public class ScriptConsoleConfigurationImpl implements ScriptConsoleConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptConsoleConfigurationImpl.class);

    private static final String SEMICOLON = ";";

    private String[] ADMIN_GROUPS = new String[] {
            "administrators"
    };

    private Set<String> allowedUsers = new CopyOnWriteArraySet();

    private Set<String> allowedGroups = new CopyOnWriteArraySet();

    @Activate
    public void activate(final UserAccessConfiguration userAccessConfiguration) {

        this.allowedGroups.addAll(Arrays.asList(ADMIN_GROUPS));
        this.allowedGroups.addAll(Arrays.asList(userAccessConfiguration.allowedGroups()));
        this.allowedUsers.addAll(Arrays.asList(userAccessConfiguration.allowedUsers()));

        if (LOG.isTraceEnabled()) {
            LOG.trace("Allowed groups [{}]", StringUtils.join(this.allowedGroups, SEMICOLON));
            LOG.trace("Allowed users [{}]", StringUtils.join(this.allowedUsers, SEMICOLON));
        }
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
