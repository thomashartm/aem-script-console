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

            /*
             editor.setTheme("ace/theme/monokai");
            editor.getSession().setMode("ace/mode/javascript");*/
        },

        initTheme: function () {

        },

        initToolbarActions: function () {

        },

        setLanguageMode: function(mode){

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