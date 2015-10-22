var AemScriptConsole = function () {

    var resultDataTable;

    return {
        initEditor: function () {

            AemScriptConsole.hidePanels();

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

            var lastScript = Lockr.get('lastScript');
            if(lastScript){
                editor.setValue(lastScript, -1);
            }
        },

        initTheme: function () {

        },

        initToolbarActions: function () {

            $('#create-new').click(function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }

                // TODO clear localstorage for this document
                // clear messages for this editor session
                AemScriptConsole.resetAllMessages();

                editor.getSession().setValue('');
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

                    editor.setReadOnly(true);
                    var posting = $.post(CQ.shared.HTTP.getContextPath() + '/bin/asconsole/groovy/post.json', {
                            script: script
                    });

                    posting.done(function (xhrMessage) {
                        window.console.log("Done");
                        window.console.log(xhrMessage);

                        AemScriptConsole.clearPanels();

                        if(xhrMessage.failed){
                            AemScriptConsole.setPanelVisibility(true, true);
                            $(".info-error").append(xhrMessage.error);
                        }else{
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
                AemScriptConsole.saveScript(script);
            });

            $('#clear-editor').click(function () {
                AemScriptConsole.clearPanels();
                AemScriptConsole.clearEditor();
                var message = "Editor and localStorage [lastScript] cleared";
                AemScriptConsole.printToMeta(message);
            });
        },

        saveScript: function(script){
            Lockr.set('lastScript', script);
            AemScriptConsole.printToMeta("Script saved.");
        },

        clearEditor: function(){
            //Lockr.rm('lastScript');
            //editor.destroy();
        },

        setLanguageMode: function (mode) {

        },

        resetAllMessages: function(){
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
        },

        printToMeta: function(message){
            window.console.log(message);

            $(".info-meta").fadeIn('fast');
            $(".info-meta").text(message);
            setTimeout(function () {
                $(".info-meta").fadeOut('slow');
                $(".panel-meta").hide();
            }, 2500);
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