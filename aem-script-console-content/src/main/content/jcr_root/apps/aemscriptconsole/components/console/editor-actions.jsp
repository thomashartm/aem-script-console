<%@page session="false" %>
<%@include file="/libs/granite/ui/global.jsp" %>

<div class="endor-Panel-header foundation-layout-mode3"
     data-foundation-layout="{&quot;name&quot;:&quot;foundation-layout-mode2&quot;,&quot;group&quot;:&quot;aemscriptconsole-mode&quot;}">
    <nav class="foundation-layout-mode2-item foundation-layout-mode2-item-active endor-ActionBar js-granite-endor-ActionBar"
         data-foundation-layout-mode2-item-mode="default">
        <div class="endor-ActionBar-left" style="width: 398px;">
            <button id="execute-script"  class="endor-ActionBar-item coral-Button coral-Button--quiet coral-Button--graniteActionBar"
                    type="button" autocomplete="off" title="Code Editor">
                <i class="coral-Icon coral-Icon--checkCircle"></i><span><%= i18n.get("Execute") %></span>
            </button>

            <button id="clear-editor"  class="endor-ActionBar-item coral-Button coral-Button--quiet coral-Button--graniteActionBar"
                    type="button" autocomplete="off" title="Code Editor">
                <i class="coral-Icon coral-Icon--delete"></i><span><%= i18n.get("Clear") %></span>
            </button>

            <!--
            <button  type="button" class="btn btn-default" aria-label="Left Align">
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
            </button>-->
        </div>
    </nav>
</div>