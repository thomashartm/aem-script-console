<%@include file="/libs/foundation/global.jsp" %>
<c:choose>
    <c:when test="${isAuthor}">
        <cq:includeClientLib categories="cq.wcm.edit,aemscriptconsole" />
    </c:when>
    <c:otherwise>
        <cq:includeClientLib categories="cq.shared,aemscriptconsole" />
    </c:otherwise>
</c:choose>
<div class="panel panel-default" id="consolearea">
    <div class="panel-heading">
        Filename: xxx   &nbsp; JCR Path with Link: xxx
    </div>
    <cq:include script="editor-actions.jsp" />
    <div id="resizable" class="panel-body editorarea">
        <div id="editor">
        </div>
    </div>
    <cq:include script="outputpanels.jsp" />
</div>

<div class="panel panel-default" id="formarea">
    <cq:include script="formeditor.jsp" />
</div>

<div class="panel-footer">
</div>