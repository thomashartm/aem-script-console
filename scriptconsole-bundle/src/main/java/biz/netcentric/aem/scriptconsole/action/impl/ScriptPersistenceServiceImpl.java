package biz.netcentric.aem.scriptconsole.action.impl;

import biz.netcentric.aem.scriptconsole.ScriptPersistenceService;
import com.day.cq.wcm.api.PageManagerFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 12/2016
 */
@Service
@Component(metatype = false)
public class ScriptPersistenceServiceImpl implements ScriptPersistenceService {

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Override
    public void createScriptContainer(final String location, final String name, final String extension) {

    }
}
