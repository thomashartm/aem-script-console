<%@page session="false" %>
<%@include file="/libs/granite/ui/global.jsp" %>

<div class="panel-heading">
    <article class="foundation-collection-item card-page" data-profile-title="Whatever" data-profile-name="Whatever"
             data-profile-path="Path">
        <button class="asconsole-create-new-parameter endor-ActionBar-item coral-Button coral-Button--quiet coral-Button--graniteActionBar"
                type="button" autocomplete="off" title="Add Parameter"><i class="coral-Icon coral-Icon--add"></i><span><%= i18n
                .get("Add Parameter") %></span></button>
    </article>
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