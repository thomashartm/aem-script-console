<%@include file="/libs/foundation/global.jsp"%>
<head>
    <title>AEM Script Console</title>
    <c:choose>
        <c:when test="${isAuthor}">
            <cq:includeClientLib categories="cq.wcm.edit,aemscriptconsole" />
        </c:when>
        <c:otherwise>
            <cq:includeClientLib categories="cq.shared,aemscriptconsole" />
        </c:otherwise>
    </c:choose>
    <style type="text/css" media="screen">

    </style>
</head>