
package com.demo.aem.core.utils;

import java.util.Comparator;
import java.util.Date;

import org.apache.sling.api.resource.Resource;

public class NewsResourceComparator implements Comparator<Resource> {

    @Override
    public int compare(Resource news1, Resource news2) {
        boolean isStickyNews1 = false;
        boolean isStickyNews2 = false;
        if (news1.getValueMap().containsKey("isStickyNews")
                && news1.getValueMap().get("isStickyNews", String.class).equalsIgnoreCase("true")) {
            isStickyNews1 = true;
        }
        if (news2.getValueMap().containsKey("isStickyNews")
                && news2.getValueMap().get("isStickyNews", String.class).equalsIgnoreCase("true")) {
            isStickyNews2 = true;
        }
        Date news1PublishedDate = news1.getValueMap().get("publishedDate", Date.class);
        Date news2PublishedDate = news2.getValueMap().get("publishedDate", Date.class);
        if ((isStickyNews1 && isStickyNews2) || (!isStickyNews1 && !isStickyNews2)) {
            return news2PublishedDate.compareTo(news1PublishedDate);
        } else if (isStickyNews1 && !isStickyNews2) {
            return 1;
        } else {
            return -1;
        }
    }
}