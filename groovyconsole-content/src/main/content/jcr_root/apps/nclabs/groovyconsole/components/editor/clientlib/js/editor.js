(function ($, $document, gAuthor) {

    const LAST_SCRIPT = "lastScript"
    const SAVED_SCRIPT = "savedScript"
    let EDITOR_ID = "#editor";
    let consoleToAreaWidthRatio = 0.8,
        consoleToAreaHeightRatio = 0.8;

    const ui = $(window).adaptTo("foundation-ui");

    /**
     * Editor initialization, styling and sizing
     */
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
        let lastScript = Lockr.get(LAST_SCRIPT);
        if (lastScript) {
            editor.setValue(lastScript, -1);
        }

        $('#infoarea').show();
    };

    let clearEditor = function () {
        Lockr.rm(LAST_SCRIPT);
        editor.setValue("");
    };

    let saveScriptToLocalStore = function (script) {
        Lockr.set(LAST_SCRIPT, script);
        printToMeta("Script saved to localStorage let [lastScript]");
    };

    let focusEndOfEditorDocument = function () {
        editor.focus();
        let session = editor.getSession();
        let count = session.getLength();
        editor.gotoLine(0, session.getLine(count - 1).length);
    };

    /**
     * Toolbar action click event handling
     */
    let initializeEditorToolbar = function () {

        $('.create-new').click(function () {
            if ($(this).hasClass('disabled')) {
                return;
            }

            // clear message and local storage for this editor session
            Lockr.rm(LAST_SCRIPT);

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
            let scriptPath = Lockr.get('scriptPath')
            if (!scriptPath) {
                console.log("No path defined")
                scriptPath = ""
            }
            console.log("Saving: + " + scriptPath)

            let scriptName = Lockr.get('scriptName')
            if (!scriptName) {
                console.log("No path defined")
                scriptName = ""
            }
            console.log("Saving: + " + scriptName)

            let script = editor.getSession().getValue();
            if (script) {
                console.log("No script available")
            }

            editor.setReadOnly(true)
            let saveGroovyScript = $.post('/bin/nclabs/groovyconsole/save.json', {
                script: script,
                path: scriptPath
            })

            saveGroovyScript.done(function (xhrMessage) {
                console.log(xhrMessage);
            })


            saveGroovyScript.fail(function (xhrMessage) {
                console.log(xhrMessage);
            })

            saveGroovyScript.always(function () {
                editor.setReadOnly(false);
            })

        });

    };

    /**
     * Result rendering and handling
     */
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

    let clearInfoPanel = function () {
        let infoArea = $("#infoarea");
        infoArea.empty();
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

    /**
     * Shows / hides the "Save As Script window" modal
     * @param show
     */
    let toggleSaveAsScriptModal = function (show, callbackListener) {
        var saveAsScriptModal = document.getElementById("groovyconsole-save-script-dialog");
        if (saveAsScriptModal) {
            if (show) {
                saveAsScriptModal.show();
                // execute the listener only once per show event
                $(saveAsScriptModal).one('coral-overlay:open', function (event) {
                    if (callbackListener) {
                        callbackListener();
                    }
                });
            } else {
                saveAsScriptModal.hide();
            }
        }
    }

    /**
     * Initialization, loading and unloading
     */
    $(document).ready(function () {
        console.log("Loading editor... ");
        styleEditor();
        sizeEditor();
        showEditor();
        initializeEditorToolbar();
        initScriptNameLabel();
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

    $(window).adaptTo("foundation-registry").register("foundation.collection.action.action", {
        name: "groovyconsole.action.saveasscript",
        handler: function (name, el, config, collection, selections) {
            printToMeta("modal loaded")
        }
    })

    // open the save modal
    $(document).on("click", ".save-as-script", function (e) {
        toggleSaveAsScriptModal(true, function () {
            printToMeta("Save as script modal loaded")

            const scriptData = Lockr.get(SAVED_SCRIPT, {})
            printToMeta(scriptData)
            $("input[name=savePath]").val(scriptData.path)
            $("input[name=scriptName]").val(scriptData.name)
        })
    });

    const initScriptNameLabel = function(){
        const label = document.getElementById("groovyconsole-editor-title");
        const lastSavedScript = Lockr.get(SAVED_SCRIPT, {})
        if(lastSavedScript.name){
            label.innerText = lastSavedScript.name
        }else{
            label.innerText = "Unknown"
        }
    }

    // submit and close the save modal
    $(document).on("click", "#groovyconsole-save-script-action-btn", function (e) {
        // make sure the parent container under conf is configured for collection and property inheritance

        const scriptType = "groovy"

        const savePath = $.trim($("input[name=savePath]").val())
        if ((!savePath) || (savePath.length == 0)) {
            showError("Error", "Unable to save script. Save path argument is empty.")
            return
        }

        const scriptName = $.trim($("input[name=scriptName]").val())
        if ((!scriptName) || (scriptName.length == 0)) {
            showError("Error", "Unable to save script. Name argument is empty.")
            return
        }

        var script = editor.getSession().getValue()
        if ((!script) || (script.length == 0)) {
            showError("Error", "Unable to save script. Script argument is empty.")
            return
        }

        editor.setReadOnly(true);
        const scriptLocation = savePath + "/" + scriptName + "." + scriptType

        const data = {
            "_charset_": "UTF-8",
            "script": script,
            "scriptType": "groovy",
            "name": scriptName,
            "path": savePath,
            "fullLocation": scriptLocation
        }

        let request = $.ajax({
            type: "POST",
            url: "/bin/nclabs/groovyconsole/save.json",
            data: data
        })

        // Callback handler that will be called on success
        request.done(function (response, textStatus, jqXHR){
            // Log a message to the console
            console.log("Hooray, it worked!")
            printToMeta("Successfully saved script " + scriptLocation)
            Lockr.set(SAVED_SCRIPT, data)

            initScriptNameLabel();
        })

        // Callback handler that will be called on failure
        request.fail(function (xhr, textStatus, errorThrown){
            // Log the error to the console
            console.error(
                "The following error occurred: "+
                textStatus, errorThrown
            )

            console.log(xhr)
            printToMeta("Error during page creation")
        })

        // Callback handler that will be called regardless
        // if the request failed or succeeded
        request.always(function () {
            // hide the save dialog
            toggleSaveAsScriptModal(false, {})
            // Reenable the inputs
            editor.setReadOnly(false)
        })

        event.preventDefault()
    })
})
(Granite.$, $(document), Granite.author);