(function ($, $document, gAuthor) {

    var registerDragAndDrop = function () {
        $(".dragvert-container").on("taphold mousedown", function (e) {
            var element = $(e.target),
                source = $(this),
                target = $(".dragvert-container").not(source);
            //
            // Setting fixed height to avoid resizing of container during drag
            source.height(source.height());
            //
            // Start Drag'n'drop
            new CUI.DragAction(e, source, element, [target]);
        }).on("dragenter", function (e) {
            //
            // Making space for new element
            $(this).css("height", $(this).height() + e.item.outerHeight(true));

            var toogleRemove = $(e.target).hasClass("fe-dragvert-source");
            $(e.item).toggleClass("remove-element", toogleRemove);

        }).on("dragleave", function (e) {
            //
            // Setting height of target back to auto after element left boundaries
            // of target
            $(this).css("height", "auto");
        }).on("dragend", function (e) {
            //
            // Setting height of source back to auto after drag is finished
            e.sourceElement.css("height", "auto");
        }).on("drop", function (e) {
            //
            // Stop Drag'n'drop - move element to new position in DOM
            if ($(e.target).hasClass("fe-dragvert-source")) {
                $(e.item).remove();
                clearedPropertiesForm();
            } else {
                var targetElement = $(e.item).clone();
                targetElement.data("formFieldId", "field" + getAndIncrementContainerIndex());
                console.log(targetElement.data());
                targetElement.appendTo(this);
            }
        });
    }

    $(document).on("dblclick", ".fe-dragvert-target .dragvert-element", function (e) {
        createPropertiesForm(e);
    });

    var createPropertiesForm = function (e) {
        var fieldLocation = clearedPropertiesForm();

        var element = $(e.currentTarget);

        var type = element.data("formElementType");

        if (type === "textfield") {
            addTextInputField(fieldLocation, element, {value: element.data("formFieldId"), type: type, label: "Field ID"});
            addTextInputField(fieldLocation, element, {value: 200, type: type, label: "Max Length"});
            addCheckboxField(fieldLocation, element, {value: "true", id: "mandatory", label: "Mandatory"})
        } else {
            console.log("I'm afraid this type is not supported yet");
        }
        console.log("Handler for .dblclick() called: " + element.data("formElementType"));
    };

    var addTextInputField = function (parent, element, settings) {
        $("<label class='coral-Form-fieldlabel'>" + settings.label + "</label>")
            .appendTo(parent);

        $('<input type="text" placeholder="Required" aria-required="true">')
            .addClass("coral-Form-field")
            .addClass("coral-Textfield")
            .attr("value", settings.value)
            .appendTo(parent);
    };

    var addCheckboxField = function (parent, element, settings) {
        var label = $("<label class='coral-Checkbox'></label>");
        label.appendTo(parent);

        $("<input class='coral-Checkbox-input' type='checkbox' checked />")
            .attr("name", settings.id)
            .attr("value", settings.value)
            .appendTo(label);
        $("<span class='coral-Checkbox-checkmark'></span>").appendTo(label);
        $("<span class='coral-Checkbox-description'>" + settings.label + "</span>").appendTo(label);

    };

    var getTargetContainerIndex = function () {
        var target = $(".fe-dragvert-target");
        return parseInt(target.data("containerOpsCounter"));
    };

    var getAndIncrementContainerIndex = function () {
        var target = $(".fe-dragvert-target");
        var current = parseInt(target.data("containerOpsCounter"));
        target.data("containerOpsCounter", current + 1);
        return current;
    };

    var clearedPropertiesForm = function(){
        var target = $(".properties-container .coral-Form-fieldset");
        target.empty();
        return target;
    };

    $(document).ready(function () {
        console.log("Loading FormEditor... ");
        registerDragAndDrop();
        console.log("FormEditor has been successfully loaded... ");
    });
})(Granite.$, $(document), Granite.author);