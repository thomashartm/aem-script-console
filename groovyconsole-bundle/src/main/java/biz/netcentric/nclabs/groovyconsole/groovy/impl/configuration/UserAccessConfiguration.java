package biz.netcentric.nclabs.groovyconsole.groovy.impl.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.List;
import java.util.Set;

/**
 * OSGi configuration for the script console
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
@ObjectClassDefinition(name = "Groovy Scriptconsole UserAccessConfiguration")
public @interface UserAccessConfiguration {

    @AttributeDefinition(
            name = "Allowed Users"
    )
    String[] allowedUsers();

    @AttributeDefinition(
            name = "Allowed Groups"
    )
    String[] allowedGroups();
}
