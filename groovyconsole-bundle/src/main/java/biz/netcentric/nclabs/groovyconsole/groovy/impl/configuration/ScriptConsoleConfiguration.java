package biz.netcentric.nclabs.groovyconsole.groovy.impl.configuration;

import java.util.Set;

/**
 * OSGi configuration for the script console
 *
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2018
 */
public interface ScriptConsoleConfiguration {

    /**
     * Allowed users for executing the console
     *
     * @return
     */
    Set<String> getAllowedGroups();

    /**
     * Allowed groups for executing the console
     *
     * @return
     */
    Set<String> getAllowedUsers();
}
