<%@include file="/libs/foundation/global.jsp"%>
<body>


    <div class="panel panel-default">
        <div class="panel-heading">
            Filename: xxx   &nbsp; JCR Path with Link: xxx
        </div>
        <div class="btn-group" role="group" aria-label="editor-actions">
            <button id="execute-script" type="button" class="btn btn-default" aria-label="Left Align">
                <span class="glyphicon glyphicon-ok" aria-hidden="true">&nbsp;Execute</span>
            </button>
            <button id="create-new" type="button" class="btn btn-default" aria-label="Left Align">
                <span class="glyphicon glyphicon-file" aria-hidden="true">&nbsp;New</span>
            </button>
            <button id="save-script" type="button" class="btn btn-default" aria-label="Left Align">
                <span class="glyphicon glyphicon-save" aria-hidden="true">&nbsp;Save</span>
            </button>
            <button id="clear-editor" type="button" class="btn btn-default" aria-label="Left Align">
                <span class="glyphicon glyphicon-remove" aria-hidden="true">&nbsp;Clear</span>
            </button>
            <button id="configure-console" type="button" class="btn btn-default" aria-label="Left Align">
                <span class="glyphicon glyphicon-cog" aria-hidden="true">&nbsp;Configure</span>
            </button>
        </div>
        <div id="resizable" class="panel-body editorarea">
            <div id="editor">
            </div>
        </div>
        <div class="panel-body editormessage">
            <div class="panel panel-danger">
                <div class="panel-heading">Error</div>
                <div class="panel-body info-error">
                </div>
            </div>
            <div class="panel panel-info panel-output">
                <div class="panel-heading">Output</div>
                <div class="panel-body info-output">
                </div>
            </div>
            <div class="panel panel-default panel-meta">
                <div class="panel-body info-meta">
                </div>
            </div>
        </div>
        <div class="panel-footer">


        </div>

    </div>
    <div class="text-center">
        End of editor text ...
    </div>

<script>

</script>

</body>