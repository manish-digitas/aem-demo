package com.demo.aem.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.demo.aem.core.service.QueryResourceService;

@Component(service = QueryResourceService.class, immediate = true)
@ServiceDescription("Service to return all the resources fetched as a result of a JCR query")
public class QueryResourceServiceImpl implements QueryResourceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverFactory resolverFactory;

    private static final String SUB_SERVICE_NAME = "datawrite";

    @Override
    public List<Resource> getResources(PredicateGroup group, Session session) {
        Query query = queryBuilder.createQuery(group, session);
        logger.info("Search Query : {}", query.getPredicates().toString());
        // Get search results
        if (null != query) {
            SearchResult result = query.getResult();
            List<Hit> list = result.getHits();
            List<Resource> resourceList = new ArrayList<>();
            for (Hit hit : list) {
                try {
                    resourceList.add(hit.getResource());
                } catch (RepositoryException e) {
                    logger.error("Error message : {}", e.getMessage());
                }
            }
            return resourceList;
        }
        return null;

    }

    @Override
    public Session getAdminSession(ResourceResolver resolver) {

        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SUB_SERVICE_NAME);
        try {
            resolver = resolverFactory.getServiceResourceResolver(param);

            Session session = resolver.adaptTo(Session.class);
            if (null != session)
                return session;

        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    @Override
    public List<Resource> getResourcesByMap(Map<String, String> map, Session session) {
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        logger.info("Session user : {}", session.getUserID());
        logger.info("Search Query : {}", query.getPredicates().toString());

        if (null != query) {
            SearchResult result = query.getResult();
            List<Hit> list = result.getHits();
            List<Resource> resourceList = new ArrayList<>();
            for (Hit hit : list) {
                try {
                    resourceList.add(hit.getResource());
                } catch (RepositoryException e) {
                    logger.error("Error message : {}", e.getMessage());
                }
            }

            return resourceList;
        }
        return null;
    }

}
