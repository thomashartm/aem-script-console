var AemScriptConsole = function () {

    var resultDataTable;

    return {
        initEditor: function () {
            var editor = ace.edit("editor");
            editor.setTheme("ace/theme/twilight");
            (function () {
                editor.session.setMode("ace/mode/groovy");
            }());
        },

        initTheme: function () {

        },

        initToolbarActions: function () {
            $('#execute-script').click(function () {
                if ($('#execute-script').hasClass('disabled')) {
                    return;
                }

                AemScriptConsole.clear();

                var script = editor.getSession().getValue();
                if (script.length) {

                    editor.setReadOnly(true);
                    $.post(CQ.shared.HTTP.getContextPath() + '/bin/asconsole/groovy/post', {
                        script: script
                    }).done(function (response) {
                        //show results
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
                    });
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