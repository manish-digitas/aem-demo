package com.demo.aem.core.service;

import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.PredicateGroup;

public interface QueryResourceService {
    public List<Resource> getResources(PredicateGroup group, Session session);

    public List<Resource> getResourcesByMap(Map<String, String> map, Session session);

    public Session getAdminSession(ResourceResolver resolver);
}