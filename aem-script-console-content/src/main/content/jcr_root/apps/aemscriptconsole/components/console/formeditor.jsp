<%@page session="false" %>
<%@include file="/libs/granite/ui/global.jsp" %>
<div class="panel-body formeditorarea">

    <div id="listcontainer" data-fetchSize='30' data-size="1" data-src="">
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


        <!--header class="card-asset selectable">
        <div style="position:absolute; left:15px;">
          <input id="selectAllToggle" type='checkbox' /><span />
        </div>
        <i class="sort"></i>
        <div class="label">
          <div class='main' data-title='TITLE' data-sort-attribute='id' data-sort-selector='.label&#x20;h4'>
  				  <a href="#" onclick="javascript:sortBy('id')"><%= i18n.get("Preset Title") %></a>
  		    </div>
          <div id="typecontainer" class='type' data-title='TYPE' data-sort-selector='.label&#x20;.type' >
            <a id="typelink" href="#" onclick="javascript:sortBy('category')"><%= i18n.get("Type") %></a>
          </div>
          <div id="statecontainer" class='size' data-sort-type='numeric' data-sort-attribute='data-bytes' data-title='SIZE' data-sort-selector='.label&#x20;.size' >
            <a id="statelink" href="#" onclick="javascript:sortBy('isactive')"><%= i18n.get("State") %></a>
          </div>
        </div>
      </header-->
    </div>


    <article class="foundation-collection-item card-page" data-profile-title="Whatever" data-profile-name="Whatever"
             data-profile-path="Path">
        <i class="select"></i>
        <a href="#">
            <div class="label">
                <h4>Whatever</h4>
            </div>
        </a>
    </article>

</div>
</div>
