package com.demo.aem.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.Predicate;
import com.day.cq.search.PredicateGroup;
import com.demo.aem.core.service.QueryResourceService;

@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/queryResourcesTest")
@ServiceDescription("Test Servlet to fetch query response")
public class TestQueryServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference
    private QueryResourceService queryResourceService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("parentPath");
        String resourceType = req.getParameter("resourceType");

        log.info(" inside TestQueryServlet ::: path : {}", path);
        log.info(" inside TestQueryServlet ::: resourceType : {}", resourceType);

        // Session session = req.getResourceResolver().adaptTo(Session.class);
        Session session = queryResourceService.getAdminSession(req.getResourceResolver());

        PredicateGroup group = new PredicateGroup();
        group.add(new Predicate("parentPath", "path").set("path", path));
        group.add(new Predicate("type", "type").set("type", "nt:unstructured"));
        group.add(new Predicate("property", "property").set("property", "sling:resourceType"));
        group.add(new Predicate("propertyValue", "property.value").set("propertyValue", resourceType));
        group.add(new Predicate("hits", "p.hits").set("hits", "full"));
        group.add(new Predicate("limit", "p.limit").set("limit", String.valueOf(-1)));

        List<Resource> resourceList = queryResourceService.getResources(group, session);
        resp.setContentType("text/plain");
        resp.getWriter().write(resourceList.toString());
    }
}
