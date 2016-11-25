<%@page session="false" contentType="text/html" pageEncoding="utf-8" %>
<%@include file="/libs/granite/ui/global.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<sling:adaptTo adaptable="${slingRequest}" adaptTo="net.thartm.aem.asconsole.models.FormBuilder" var="fb"/>
<div id="form-builder-view">
    <div id="form-panel" class="panel">
        <ol id="form-fields" class="list-fields">
            <c:forEach var="field" items="${fb.formFields}" varStatus="status">
                <li id="${field.name}-view" data-id="${field.name}" class="field" data-fieldtype="${field.type}">
                    <sling:include resource="${field.resource}"/>
                </li>
            </c:forEach>
        </ol>
    </div>
</div>