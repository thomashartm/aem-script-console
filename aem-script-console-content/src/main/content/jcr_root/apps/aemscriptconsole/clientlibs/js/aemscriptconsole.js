var AemScriptConsole = function () {

    var resultDataTable;

    return {
        initEditor: function () {
            var editor = ace.edit("editor");
            editor.setTheme("ace/theme/twilight");
            (function () {
                var modelist = ace.require("ace/ext/modelist");
                // the file path could come from an xmlhttp request, a drop event,
                // or any other scriptable file loading process.
                // Extensions could consume the modelist and use it to dynamically
                // set the editor mode. Webmasters could use it in their scripts
                // for site specific purposes as well.
                //var filePath = "blahblah/weee/some.js";
                //var mode = modelist.getModeForPath(filePath).mode;
                //console.log(mode);
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