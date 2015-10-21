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


            $( "#resizable" ).resizable({
                resize: function( event, ui ) {
                    editor.resize();
                }
            });
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

                    posting.done(function (xhrMessage) {
                        window.console.log("Done");
                        window.console.log(xhrMessage);

                        AemScriptConsole.clear();

                        if(xhrMessage.failed){
                            AemScriptConsole.setPanelVisibility(true, true);
                            $(".info-error").append(xhrMessage.error);
                        }else{
                            AemScriptConsole.setPanelVisibility(true, false);
                        }

                        $(".info-output").append(xhrMessage.output);
                        $(".info-meta").append(xhrMessage.executionTime + " ms");
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
            $(".info-error").empty();
            $(".info-output").empty();
            $(".info-meta").empty();
        },

        setPanelVisibility: function(output, error){
            if(output){
                $(".panel-output").show();
            }else{
                $(".panel-output").hide();
            }

            if(error){
                $(".panel-danger").show();
            }else{
                $(".panel-danger").hide();
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