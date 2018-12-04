(function ($, $document, gAuthor) {

    let EDITOR_ID = "#editor";
    let consoleToAreaWidthRatio = 0.9,
        consoleToAreaHeightRatio = 0.9;

    let saveModal;

    let sizeEditor = function () {
        let windowHeight = $(window).innerHeight();
        let defaultHeight = windowHeight * consoleToAreaHeightRatio;
        $('#resizable').css('height', Lockr.get('editorHeight', defaultHeight));

        let windowWidth = $(window).innerWidth();
        $('#resizable').css('width', Lockr.get('editorWidth', windowWidth * consoleToAreaWidthRatio));

        editor.setAutoScrollEditorIntoView(true);
        editor.resize();
    };

    let styleEditor = function () {
        window.editor = ace.edit("editor");
        editor.setTheme("ace/theme/dracula");
        (function () {
            editor.session.setMode("ace/mode/groovy");
        }());
    };

    let showEditor = function () {
        let lastScript = Lockr.get('lastScript');
        if (lastScript) {
            editor.setValue(lastScript, -1);
        }

        $('#infoarea').show();
    };

    let clearInfoPanel = function () {
        let infoArea = $("#infoarea");
        infoArea.empty();
    };

    let clearEditor = function () {
        Lockr.rm('lastScript');
        editor.setValue("");
    };

    let initializeEditorToolbar = function () {

        $('.create-new').click(function () {
            if ($(this).hasClass('disabled')) {
                return;
            }

            // clear message and local storage for this editor session
            Lockr.rm('lastScript');

            clearInfoPanel();
            editor.getSession().setValue('def resource = resourceResolver.getResource("/content"); \nprintln resource.path');
            printToMeta("New document created.");
        });

        $('.execute-script').click(function () {
            console.log("Triggered script execution... ");
            if ($(this).hasClass('disabled')) {
                return;
            }

            clearInfoPanel();

            let script = editor.getSession().getValue();
            if (script.length) {

                editor.setReadOnly(true);
                let postGroovyScript = $.post('/bin/nclabs/groovyconsole/execute.json', {
                    script: script
                });

                saveScriptToLocalStore(script);

                postGroovyScript.done(function (xhrMessage) {
                    console.log(xhrMessage);
                    clearInfoPanel();

                    if (xhrMessage.failed) {
                        showError("Script failed", xhrMessage.error);
                    }

                    if (xhrMessage.result && xhrMessage.result !== "null" && xhrMessage.result !== "") {
                        showResult("Script Result", xhrMessage.result);
                    }

                    if (xhrMessage.output && xhrMessage.output !== "null" && xhrMessage.output !== "") {
                        showOutput("Output", xhrMessage.output);
                    }

                    printToMeta(xhrMessage.executionTime + " ms");
                });

                postGroovyScript.fail(function (xhrMessage) {
                    if (xhrMessage.failed && xhrMessage.status == 403) {
                        //missing permissions
                        showError("Missing permissions")
                    } else {
                        showError("Unable to execute script");
                    }
                });

                postGroovyScript.always(function () {
                    editor.setReadOnly(false);
                });
            }

            focusEndOfEditorDocument();
        });

        $('.clear-editor').click(function () {
            clearInfoPanel();
            clearEditor();

            printToMeta("Editor and localStorage [lastScript] cleared");

            focusEndOfEditorDocument();
        });

        $('.save-script').click(function () {
            const scriptPath = Lockr.get('scriptPath');
            if (!scriptPath) {
                console.log("No path defined");
            }
            console.log("Saving: + " + scriptPath);

            let script = editor.getSession().getValue();
            if (script) {
                console.log("No script available");
            }

            editor.setReadOnly(true);
            let saveGroovyScript = $.post('/bin/nclabs/groovyconsole/save.json', {
                script: script,
                path: scriptPath
            });

            saveGroovyScript.done(function (xhrMessage) {
                console.log(xhrMessage);
            });


            saveGroovyScript.fail(function (xhrMessage) {
                console.log(xhrMessage);
            });

            saveGroovyScript.always(function () {
                editor.setReadOnly(false);
            });

        });

    };

    let showError = function (header, message) {
        displayInInfoPanel("error", header, message);
    };

    let showOutput = function (header, message) {
        displayInInfoPanel("info", header, message);
    };

    let showResult = function (header, message) {
        displayInInfoPanel("success", header, message);
    };

    let displayInInfoPanel = function (variant, header, message) {
        var infoBox = new Coral.Alert().set({
            size: "L",
            variant: variant,
            header: {
                innerHTML: header
            },
            content: {
                innerHTML: message
            }
        });

        let infoArea = $("#infoarea");
        infoBox.classList.add("widebox");
        infoArea.append(infoBox);
    };

    let saveScriptToLocalStore = function (script) {
        Lockr.set('lastScript', script);
        printToMeta("Script saved to localStorage let [lastScript]");
    };

    let focusEndOfEditorDocument = function () {
        editor.focus();
        let session = editor.getSession();
        let count = session.getLength();
        editor.gotoLine(0, session.getLine(count - 1).length);
    };

    let printToMeta = function (message) {
        window.console.log(message);
        /*window.console.log(message);
        $(".info-meta").show();
        $(".info-meta").fadeIn('slow');
        $(".info-message-meta").text(message);
        setTimeout(function () {
            $(".info-meta").fadeOut('slow');
            $(".info-meta").hide();
        }, 3500);*/
    };

    $(document).ready(function () {
        console.log("Loading editor... ");
        styleEditor();
        sizeEditor();
        showEditor();
        initializeEditorToolbar();
        printToMeta("Editor has been successfully loaded... ");
    });

    window.addEventListener("beforeunload", function (event) {
        // Cancel the event as stated by the standard.
        event.preventDefault();
        // Chrome requires returnValue to be set.
        event.returnValue = '';

        let script = editor.getSession().getValue();
        saveScriptToLocalStore(script);
    });

})
(Granite.$, $(document), Granite.author);