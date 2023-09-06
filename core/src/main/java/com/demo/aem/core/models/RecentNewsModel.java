package com.demo.aem.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.aem.core.service.QueryResourceService;
import com.demo.aem.core.utils.NewsResourceComparator;

@Model(adaptables = Resource.class)
public class RecentNewsModel {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "No resourceType")
    protected String resourceType;

    @ValueMapValue
    private String experienceFragmentPath;

    @ValueMapValue
    private String slingResourceTypeValue;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryResourceService queryResourceService;

    private static final int MAX_CARD_COUNT = 3;

    private List<Resource> resourceList;

    private boolean isAtleastOneStickyNewsCardAuthored;

    @PostConstruct
    protected void init() {
        log.info(" inside QueryResourceModel ::: experienceFragmentPath : {}", experienceFragmentPath);
        log.info(" inside QueryResourceModel ::: slingResourceTypeValue : {}", slingResourceTypeValue);

        Map<String, String> map = new HashMap<>();
        map.put("path", experienceFragmentPath);
        map.put("type", "nt:unstructured");
        map.put("property", "sling:resourceType");
        map.put("property.value", slingResourceTypeValue);
        map.put("p.hits", "full");
        map.put("p.limit", "-1");

        Session session = queryResourceService.getAdminSession(resourceResolver);
        try {
            if (session != null) {
                resourceList = queryResourceService.getResourcesByMap(map, session);
                Collections.sort(resourceList, new NewsResourceComparator());
            }
        } catch (Exception e) {
            log.error("Admin session is null : {}", Arrays.toString(e.getStackTrace()));
        }
    }

    public List<Resource> getResourceList() {
        if (resourceList.size() > MAX_CARD_COUNT) {
            return resourceList.subList(0, MAX_CARD_COUNT);
        }
        return resourceList;
    }

}
