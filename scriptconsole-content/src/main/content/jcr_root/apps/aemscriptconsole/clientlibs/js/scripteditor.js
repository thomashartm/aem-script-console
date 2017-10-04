(function ($, $document, gAuthor) {

    var EDITOR_ID = "#editor";
    var consoleToAreaWidthRatio = 0.9,
        consoleToAreaHeightRatio = 0.5;

    var saveModal;

    var sizeEditor = function () {

        var defaultHeight = $(EDITOR_ID).height() * consoleToAreaHeightRatio;
        $('#resizable').height(Lockr.get('editorHeight', defaultHeight))

        editor.resize();
    };

    var styleEditor = function () {
        window.editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
        (function () {
            editor.session.setMode("ace/mode/groovy");
        }());
    };

    var showEditor = function () {
        var lastScript = Lockr.get('lastScript');
        if (lastScript) {
            editor.setValue(lastScript, -1);
        }

        $('#infoarea').show();
    };

    var hideAllPanels = function () {
        $(".info-error, .info-result, .info-output, .info-meta").hide();
    };

    var resetAllMessages = function () {
        clearInfoPanel();
        hideAllPanels();
    };

    var clearInfoPanel = function () {
        $(".info-message").empty();
    };

    var clearEditor = function () {
        Lockr.rm('lastScript');
        editor.setValue("");
    };

    var initializeEditorToolbar = function () {

        $('.create-new').click(function () {
            if ($(this).hasClass('disabled')) {
                return;
            }

            // clear message and local storage for this editor session
            Lockr.rm('lastScript');

            resetAllMessages();
            editor.getSession().setValue('def resource = resourceResolver.getResource("/content"); \nprintln resource.path');
            printToMeta("New document created.");
        });

        $('.execute-script').click(function () {
            console.log("Triggered script execution... ");
            if ($(this).hasClass('disabled')) {
                return;
            }

            clearInfoPanel();

            var script = editor.getSession().getValue();
            if (script.length) {

                editor.setReadOnly(true);
                var posting = $.post('/bin/netcentric/scriptconsole/run/groovy.json', {
                    script: script
                });

                posting.done(function (xhrMessage) {
                    console.log(xhrMessage);

                    clearInfoPanel();

                    if (xhrMessage.failed) {
                        setPanelVisibility(true, true, true);
                        $(".info-message-error").append(xhrMessage.error);
                    } else {
                        setPanelVisibility(true, true, false);
                    }

                    if (xhrMessage.result && xhrMessage.result !== "null" && xhrMessage.result !== "") {
                        $(".info-message-result").append(xhrMessage.result);
                    } else {
                        $(".info-message-result").append("No result");
                    }

                    if (xhrMessage.output && xhrMessage.output !== "null" && xhrMessage.output !== "") {
                        $(".info-message-output").append(xhrMessage.output);
                    } else {
                        $(".info-message-output").append("No output");
                    }
                    printToMeta(xhrMessage.executionTime + " ms");
                });

                posting.fail(function (xhrMessage) {
                    console.log("Fail");
                    console.log(xhrMessage);

                    setPanelVisibility(true, true, true);
                    if (xhrMessage.status == 403) {
                        //missing permissions
                        console.log("Missing permissions")
                    } else {
                        console.log("Unable to execute script");
                    }
                });

                posting.always(function () {
                    editor.setReadOnly(false);
                });
            }

            focusEndOfEditorDocument();
        });

        $('.clear-editor').click(function () {
            clearInfoPanel();
            clearEditor();
            hideAllPanels();

            printToMeta("Editor and localStorage [lastScript] cleared");

            focusEndOfEditorDocument();
        });
    };

    var saveScriptToLocalStore = function (script) {
        Lockr.set('lastScript', script);
        printToMeta("Script saved to localStorage var [lastScript]");
    };

    var focusEndOfEditorDocument = function () {

        editor.focus();
        var session = editor.getSession();
        var count = session.getLength();

        editor.gotoLine(count, session.getLine(count - 1).length);
    };

    setPanelVisibility = function (result, output, error) {
        if (result) {
            $(".info-result").show();
        } else {
            $(".info-result").hide();
        }

        if (output) {
            $(".info-output").show();
        } else {
            $(".info-output").hide();
        }

        if (error) {
            $(".info-error").show();
        } else {
            $(".info-error").hide();
        }
    };

    var printToMeta = function (message) {
        window.console.log(message);
        $(".info-meta").show();
        $(".info-meta").fadeIn('slow');
        $(".info-message-meta").text(message);
        setTimeout(function () {
            $(".info-meta").fadeOut('slow');
            $(".info-meta").hide();
        }, 3500);
    };

    $(document).ready(function () {
        console.log("Loading editor... ");
        hideAllPanels();
        styleEditor();
        sizeEditor();
        showEditor();
        initializeEditorToolbar();
        printToMeta("Editor has been successfully loaded... ");
    });
})
(Granite.$, $(document), Granite.author);