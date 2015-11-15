<%@page session="false" %>
<%@include file="/libs/granite/ui/global.jsp" %>
<div class="panel-body formeditorarea">
    <!--<div class="no-children-banner center">
        <a class="dam-profiles-create-profile-activator emptycontent-icon coral-Button coral-Button--square coral-Button--quiet" title="Add metadata profile" href="#createprofileform" data-toggle="modal">
            &nbsp;<i class="endor-ActionButton-icon coral-Icon coral-Icon--add"><%= i18n.get("Add Parameter") %></i>
        </a>
    </div>-->

    <div id="listcontainer" data-fetchSize='30' data-size="1" data-src="">
        <article class="foundation-collection-item card-page" data-profile-title="Whatever" data-profile-name="Whatever"
                 data-profile-path="Path">
            <i class="select"></i>
            <a class="asconsole-create-new-parameter emptycontent-icon coral-Button coral-Button--square coral-Button--quiet" title="Add parameter field" href="#addparameterfield" data-toggle="modal">
                <div class="label">
                    <%= i18n.get("Add Parameter") %>
                </div>
            </a>
        </article>
        <table class="coral-Table videotable">
            <thead class="card-video selectable">
            <tr class="coral-Table-row">
                <th class='coral-Table-headerCell'>
                    <label class='coral-Checkbox'>
                        <input id="selectAllToggle" class='coral-Checkbox-input rowSelectBoxes' type='checkbox'>
                        <span class='coral-Checkbox-checkmark'></span>
                    </label>
                </th>
                <th id="videostarts" class='coral-Table-headerCell' data-title='TITLE'><%= i18n.get("Parameter Name") %>
                </th>
                <th id="Completionrate" class='coral-Table-headerCell' data-sort-type='numeric' data-sort-attribute='data-bytes'
                    data-title='TYPE'><%= i18n.get("Type") %>
                </th>
                <th id="playpercentage" class='coral-Table-headerCell' data-sort-type='numeric' data-sort-attribute='data-bytes'
                    data-title='SIZE'><%= i18n.get("Actions") %>
                </th>
            </tr>
            </thead>
            <tbody id="viewerpresetlist">

            </tbody>
        </table>
    </div>


    <article class="foundation-collection-item card-page" data-profile-title="Whatever" data-profile-name="Whatever"
             data-profile-path="Path">
        <i class="select"></i>
        <a class="asconsole-create-new-parameter emptycontent-icon coral-Button coral-Button--square coral-Button--quiet" title="Add parameter field" href="#addparameterfield" data-toggle="modal">
            <div class="label">
                <%= i18n.get("Add Parameter") %>
            </div>
        </a>
    </article>

</div>
</div>
