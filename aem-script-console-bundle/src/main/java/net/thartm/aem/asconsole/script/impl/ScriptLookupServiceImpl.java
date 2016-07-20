package net.thartm.aem.asconsole.script.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.thartm.aem.asconsole.groovy.GroovyScript;
import net.thartm.aem.asconsole.script.Script;
import net.thartm.aem.asconsole.script.ScriptLookupService;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class ScriptLookupServiceImpl implements ScriptLookupService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    public Optional<Script> loadScript(final ResourceResolver resourceResolver, final String path) {
        // loads a script from the provided location
        final Resource resource = resourceResolver.getResource(path);
        final ValueMap valueMap = resource.getValueMap();

        final String scriptType = valueMap.get("scriptType", "");
        if(StringUtils.equals(scriptType, "groovy")) {
            final GroovyScript groovyScript = new GroovyScript(resource);
            return Optional.of(groovyScript);
        }

        return Optional.empty();
    }

    @Override
    public List<Resource> findScripts(final ResourceResolver resourceResolver, final String... terms) {

        final Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) {
            throw new IllegalStateException("Could not get session from resource resolver");
        }

        final Map<String, String> map = Maps.newHashMap();
        map.put("type", "asfc:script");
        map.put("p.hits", "full");
        map.put("p.limit", "-1");

        final Query query = this.queryBuilder.createQuery(PredicateGroup.create(map), session);
        final SearchResult result = query.getResult();

        return Lists.newArrayList(result.getResources());
    }
}
