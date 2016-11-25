package biz.netcentric.aem.scriptconsole.groovy.impl;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OSGi configuration for the script console
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2016
 */
@Service(value = ScriptConsoleConfiguration.class)
@Component(label = "Netcentric AEM Script Console Configuration", description = "Used for configuration of the AEM script console ", specVersion = "1.1", metatype = true)
public class ScriptConsoleConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptConsoleConfiguration.class);

    private String[] ADMIN_GROUPS = new String[] {
            "administrators"
    };

    private static final String ALLOWED_GROUPS = "groups.allowed";
    @Property(name = ALLOWED_GROUPS, value = StringUtils.EMPTY, label = "Allowed groups.", description = "Group allowance configuration. The listed groups are allowed to execute scripts. Separate groups by semicolon. The administrators group is set by default.")
    private Set<String> allowedGroups = new CopyOnWriteArraySet();

    @Activate
    public void activate(final ComponentContext context) {
        final Dictionary<?, ?> properties = context.getProperties();
        final String allowedGroups = PropertiesUtil.toString(properties.get(ALLOWED_GROUPS), StringUtils.EMPTY);

        final String[] splittedGroups = StringUtils.split(allowedGroups, ";");
        final Set<String> groups = Stream.of(splittedGroups).filter(g -> StringUtils.isNotBlank(g)).collect(Collectors.toSet());
        groups.addAll(Arrays.asList(ADMIN_GROUPS));

        if (LOG.isTraceEnabled()) {
            LOG.trace("Allowed groups [{}]", StringUtils.join(groups, ";"));
        }

        this.allowedGroups.addAll(groups);
    }

    public Set<String> getAllowedGroups() {
        return this.allowedGroups;
    }
}
