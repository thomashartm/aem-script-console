var AemScriptConsole = function () {

    var formInputField =
        '<tr class="coral-Table-row">' +
            '<td class="coral-Table-cell">' +
                    '<label class="coral-Checkbox">' +
                    '<input class="coral-Checkbox-input rowSelectBoxes" type="checkbox">' +
                    '<span class="coral-Checkbox-checkmark"></span>' +
                    '</label>' +
                '</td>' +
                '<td class="coral-Table-cell">' +
                    '<input class="coral-Form-field coral-Textfield" id="whatever" title="Enter Parameter Name" data-text="Enter Parameter Name" type="text" value="" />' +
                '</td>' +
                '<td class="coral-Table-cell"> Test Name </td>' +
                '<td class="coral-Table-cell">' +
                    '<span class="coral-CycleButton activeToggle" data-init="cyclebutton">' +
                        '<button class="coral-CycleButton-button coral-Button activeButton  isActiveClass " type="button" title="Card" value="on">' + Granite.I18n.get("On")
                        '</button>' +
                        '<button class="coral-CycleButton-button coral-Button activeButton  inActiveClass " type="button" title="Card" value="off">' + Granite.I18n.get("Off")
                        '</button>' +
                    '</span>' +
            '</td>' +
        '</tr>';


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

            $('#consolearea').show();
            $('#formarea').hide();
        },

        initTheme: function () {

        },

        initApplicationToolbar: function () {
            $('.asconsole-switch-to-editor').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }
                window.console.log("open editor clicked");
                $('#formarea').hide();
                $('#consolearea').show();
            });

            $('.asconsole-switch-to-form').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }
                window.console.log("open form editor clicked");

                $('#consolearea').hide();
                $('#formarea').show();
            });

            $('.asconsole-create-new-parameter').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }
                window.console.log("Create new clicked");

                $(formInputField).appendTo('#viewerpresetlist');
            });

            $('.asconsole-save-existing').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }
                window.console.log("Save editor clicked");
            });
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
    AemScriptConsole.initApplicationToolbar();
    AemScriptConsole.initEditorToolbarActions();
    AemScriptConsole.initFormToolbarActions();

});