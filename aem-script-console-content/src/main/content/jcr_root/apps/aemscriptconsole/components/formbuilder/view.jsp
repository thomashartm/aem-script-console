<%--

  ADOBE CONFIDENTIAL
  __________________

   Copyright 2012 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.

--%><%@page session="false"
            contentType="text/html"
            pageEncoding="utf-8"
            import="java.util.Collections,
          		  java.util.Iterator,
          		  net.thartm.aem.asconsole.models.FormBuilder,
          		  org.apache.sling.api.SlingHttpServletRequest,
                  org.apache.commons.lang.StringUtils,
                  org.apache.sling.api.resource.Resource,
                  org.apache.sling.api.resource.ValueMap"%><%
%><%@include file="/libs/granite/ui/global.jsp" %>
<sling:adaptTo adaptable="${slingRequest}" adaptTo="net.thartm.aem.asconsole.models.FormBuilder" var="formBuilder"/>
<div id="form-builder-view">
    ${formBuilder.formPath}
    <div id="form-panel" class="panel">
        <c:out value=""/>
        <ol id="form-fields" class="list-fields">
            <c:forEach var="field" items="${formBuilder.formElements}" varStatus="status">
                <li>Field: ${field.path}</li>
            </c:forEach>


            <%
            Iterator<Resource> general = (resource.getChild("items") != null)
                    ? resource.getChild("items").listChildren()
                    : Collections.EMPTY_LIST.iterator();
            while (general.hasNext()) {
                Resource fieldResource = general.next();
                ValueMap field = fieldResource.adaptTo(ValueMap.class);
                String type = field.get("metaType", "");
        %><li id="<%= fieldResource.getName() %>-view" data-id="<%= fieldResource.getName() %>" class="field" data-fieldtype="<%= type %>">
            <sling:include resource="<%= fieldResource %>"/>
        </li><%
            }
        %></ol>
    </div>
</div>