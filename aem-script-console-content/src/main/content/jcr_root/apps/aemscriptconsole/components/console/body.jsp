<%@include file="/libs/foundation/global.jsp"%>
<body>

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

        <cq:include script="formbuilder-actions.jsp" />
        <div class="panel-body form-builder">
            <form class="form-horizontal" id="form-builder">
            </form>
        </div>
    </div>

    <div class="panel-footer">
    </div>
<script>

</script>

</body>