var AemScriptConsole = function () {

    var formInputField =
        '<div class="col-sm-10">' +
        '<input type="text" class="form-control" id="inputParameter" placeholder="Input Parameter">' +
        '<input class="form-control" type="text" placeholder="Text Parameter" readonly>' +
        '</div>' +
        '';


    var consoleToAreaWidthRatio = 0.9;

    var consoleToAreaHeightRatio = 0.6;

    function initEditorSizing() {

        var defaultWidth = $('#consolearea').width() * consoleToAreaWidthRatio;
        $('#resizable').width(Lockr.get('editorWidth', defaultWidth));

        var defaultHeight = $('#consolearea').height() * consoleToAreaHeightRatio;
        $('#resizable').height(Lockr.get('editorHeight', defaultHeight))

        editor.resize();

        $("#resizable").resizable({
            resize: function (event, ui) {
                Lockr.set('editorWidth', $('#editor').width());
                Lockr.set('editorHeight', $('#editor').height());

                editor.resize();
            }
        });
    }

    function styleEditor() {
        window.editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
        (function () {
            editor.session.setMode("ace/mode/groovy");
        }());
    }

    return {
        initEditor: function () {

            AemScriptConsole.hidePanels();

            styleEditor();
            initEditorSizing();
            var lastScript = Lockr.get('lastScript');
            if (lastScript) {
                editor.setValue(lastScript, -1);
            }
        },

        initTheme: function () {

        },

        initEditorToolbarActions: function () {

            $('#create-new').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }

                // TODO clear localstorage for this document
                // clear messages for this editor session
                AemScriptConsole.resetAllMessages();
                editor.getSession().setValue('def resource = resourceResolver.getResource("/content"); \nprintln resource.path');
                AemScriptConsole.printToMeta("New document created.");
            });

            $('#execute-script').click(function () {
                window.console.log("Execute clicked");
                if ($(this).hasClass('disabled')) {
                    return;
                }

                AemScriptConsole.clearPanels();

                var script = editor.getSession().getValue();
                if (script.length) {
                    AemScriptConsole.saveScriptToLocalStore(script);

                    editor.setReadOnly(true);
                    var posting = $.post(CQ.shared.HTTP.getContextPath() + '/bin/asconsole/groovy/post.json', {
                        script: script
                    });

                    posting.done(function (xhrMessage) {
                        window.console.log("Done");
                        window.console.log(xhrMessage);

                        AemScriptConsole.clearPanels();

                        if (xhrMessage.failed) {
                            AemScriptConsole.setPanelVisibility(true, true);
                            $(".info-error").append(xhrMessage.error);
                        } else {
                            AemScriptConsole.setPanelVisibility(true, false);
                        }

                        $(".info-output").append(xhrMessage.output);
                        AemScriptConsole.printToMeta(xhrMessage.executionTime + " ms");
                    });

                    posting.fail(function (xhrMessage) {
                        window.console.log("Fail");
                        window.console.log(xhrMessage);

                        AemScriptConsole.setPanelVisibility(true, true);
                        if (xhrMessage.status == 403) {
                            //missing permissions
                        } else {
                            // unable to execute script
                        }
                    });

                    posting.always(function () {
                        editor.setReadOnly(false);
                        //hideLoader
                        //enable buttons
                    });
                }
            });


            $('#save-script').click(function () {
                AemScriptConsole.clearPanels();
                var script = editor.getSession().getValue()
                AemScriptConsole.saveScriptToLocalStore(script);
            });

            $('#clear-editor').click(function () {
                AemScriptConsole.clearPanels();
                AemScriptConsole.clearEditor();
                var message = "Editor and localStorage [lastScript] cleared";
                AemScriptConsole.printToMeta(message);
            });
        },

        saveScriptToLocalStore: function (script) {
            Lockr.set('lastScript', script);
            AemScriptConsole.printToMeta("Script saved to localStorage var [lastScript]");
        },

        initFormToolbarActions: function () {
            $('#add-text-field').click(function () {
                window.console.log("Add input field clicked");
                if ($(this).hasClass('disabled')) {
                    return;
                }


                $(formInputField).appendTo('#form-builder');
            });
        },

        clearEditor: function () {
            //Lockr.rm('lastScript');
            //editor.destroy();
        },

        setLanguageMode: function (mode) {

        },

        resetAllMessages: function () {
            this.clearPanels();
            this.hidePanels();
        },

        clearPanels: function () {
            $(".info-error, .info-output, .info-meta").empty();
        },

        hidePanels: function () {
            $(".panel-danger, .panel-output, .panel-meta").hide();
            window.console.log("All panels hidden");
        },

        setPanelVisibility: function (output, error) {
            if (output) {
                $(".panel-output").show();
            } else {
                $(".panel-output").hide();
            }

            if (error) {
                $(".panel-danger").show();
            } else {
                $(".panel-danger").hide();
            }
        },

        printToMeta: function (message) {
            window.console.log(message);
            $(".panel-meta").show();
            $(".info-meta").fadeIn('slow');
            $(".info-meta").text(message);
            setTimeout(function () {
                $(".info-meta").fadeOut('slow');
                $(".panel-meta").hide();
            }, 3500);
        }
    }
}();

AemScriptConsole.localStorage = function () {

}

$(function () {


    AemScriptConsole.initEditor();
    AemScriptConsole.initTheme();
    AemScriptConsole.initEditorToolbarActions();
    AemScriptConsole.initFormToolbarActions();

});