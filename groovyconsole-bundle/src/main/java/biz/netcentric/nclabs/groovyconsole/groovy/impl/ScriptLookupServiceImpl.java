package biz.netcentric.nclabs.groovyconsole.groovy.impl;

import biz.netcentric.nclabs.groovyconsole.ScriptLookupService;
import biz.netcentric.nclabs.groovyconsole.groovy.GroovyScript;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component(name = "Locates scripts in the repository", service = ScriptLookupService.class)
public class ScriptLookupServiceImpl implements ScriptLookupService{

    @Override
    public Optional<GroovyScript> loadScript(final ResourceResolver resourceResolver, final String locationPath) {
        return Optional.empty();
    }

    @Override
    public List<Resource> findScripts(final ResourceResolver resourceResolver, final String... terms) {
        return Collections.emptyList();
    }
}
