<%@include file="/libs/granite/ui/global.jsp"%><%
%><%@page session="false"%><%
%><%@page import="org.apache.sling.api.resource.Resource,
    org.apache.commons.lang.StringUtils,
    java.util.Calendar,
    org.apache.jackrabbit.util.Text,
    org.apache.sling.api.resource.ResourceResolver,
    org.apache.sling.api.resource.ValueMap,
    com.day.cq.wcm.api.Page" %><%
    Page cqPage = resource.adaptTo(Page.class);

    ValueMap properties;
    String title;



    if (cqPage != null) {
        properties = cqPage.getProperties();
        title = cqPage.getTitle();

    } else {
        properties = resource.getValueMap();
        title = properties.get("jcr:content/jcr:title", properties.get("jcr:title", resource.getName()));
    }
%>