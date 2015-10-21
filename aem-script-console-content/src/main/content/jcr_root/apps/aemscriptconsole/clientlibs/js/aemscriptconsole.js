var AemScriptConsole = function () {

    var resultDataTable;

    return {
        initEditor: function () {
            AemScriptConsole.setPanelVisibility(false, false, false);
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

                        AemScriptConsole.setPanelVisibility(true, false, false);

                        $(".info-output").empty();
                        $(".info-output").append(xhrMessage.output);
                    });

                    posting.fail(function (xhrMessage) {
                        window.console.log("Fail");
                        window.console.log(xhrMessage);

                        AemScriptConsole.setPanelVisibility(true, true, true);
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
                        $(".info-output").append(response.output);
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

        },

        setPanelVisibility: function(output, warning, error){
            if(output){
                $(".panel-output").show();
            }else{
                $(".panel-output").hide();
            }

            if(warning){
                $(".panel-warning").show();
            }else{
                $(".panel-warning").hide();
            }

            if(error){
                $(".panel-error").show();
            }else{
                $(".panel-error").hide();
            }
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