<%@page session="false" %>
<%@include file="/libs/granite/ui/global.jsp" %>
<div class="endor-Panel-header foundation-layout-mode3" data-foundation-layout="{&quot;name&quot;:&quot;foundation-layout-mode2&quot;,&quot;group&quot;:&quot;aemscriptconsole-mode&quot;}">
    <nav class="foundation-layout-mode2-item foundation-layout-mode2-item-active endor-ActionBar js-granite-endor-ActionBar" data-foundation-layout-mode2-item-mode="default">
        <div class="endor-ActionBar-left" style="width: 398px;">
            <button class="asconsole-create-new-parameter endor-ActionBar-item coral-Button coral-Button--quiet coral-Button--graniteActionBar" type="button" autocomplete="off" title="Code Editor">
                <i class="coral-Icon coral-Icon--add"></i><span><%= i18n.get("Add Parameter") %></span>
            </button>
        </div>
    </nav>
</div>

<div class="panel-body formeditorarea">

    <div id="listcontainer" data-fetchSize='30' data-size="1" data-src="">


        <table class="coral-Table videotable">
            <thead class="card-video selectable">
            <tr class="coral-Table-row">

                <th id="name" class='coral-Table-headerCell' data-title='NAME'><%= i18n.get("Parameter Name") %>
                </th>
                <th id="type" class='coral-Table-headerCell'
                    data-title='TYPE'><%= i18n.get("Type") %>
                </th>
                <th id="action" class='coral-Table-headerCell'
                    data-title='ACTION'><%= i18n.get("Actions") %>
                </th>
            </tr>
            </thead>
            <tbody id="viewerpresetlist">

            </tbody>
        </table>
    </div>


</div>