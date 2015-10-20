var AemScriptConsole = function () {

    var resultDataTable;

    return {
        initEditor: function () {
            window.editor = ace.edit("editor");
            editor.setTheme("ace/theme/twilight");
            (function () {
                editor.session.setMode("ace/mode/groovy");
            }());
        },

        initTheme: function () {

        },

        initToolbarActions: function () {

            $('#execute-script').click(function () {
                window.console.log("Execute clicked");
                if ($('#execute-script').hasClass('disabled')) {
                    return;
                }

                AemScriptConsole.clear();

                var script = editor.getSession().getValue();
                if (script.length) {

                    editor.setReadOnly(true);
                    var posting = $.post(CQ.shared.HTTP.getContextPath() + '/bin/asconsole/groovy/post.json', {
                            script: script
                    });

                    //.hide() and .show()
                    posting.done(function (xhrMessage) {
                        window.console.log("Done");
                        window.console.log(xhrMessage);
                        $(".panel-warning").hide();
                        $(".panel-error").hide();

                        $(".panel-output").show();
                        $(".panel-output").append(xhrMessage.output);
                    });

                    posting.fail(function (xhrMessage) {
                        window.console.log("Fail");
                        window.console.log(xhrMessage);

                        $(".panel-output").show();
                        $(".panel-warning").show();
                        $(".panel-error").show();
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

                /*.done(function (response) {
                        window.console.log(response);
                        $(".panel-output").append(response.output);
                    }).fail(function (xhrMessage) {
                        if (xhrMessage.status == 403) {
                            //missing permissions
                        } else {
                            // unable to execute script
                        }
                    }).always(function () {
                        editor.setReadOnly(false);

                        //hideLoader
                        //enable buttons
                    });*/
                }
            });
        },

        setLanguageMode: function (mode) {

        },

        clear: function () {

        }
    }
}();

AemScriptConsole.localStorage = function () {

}

$(function () {
    AemScriptConsole.initEditor();
    AemScriptConsole.initTheme();
    AemScriptConsole.initToolbarActions();


});