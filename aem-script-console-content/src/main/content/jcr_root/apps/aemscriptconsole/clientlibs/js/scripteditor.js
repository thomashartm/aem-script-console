(function ($, $document, gAuthor) {

    var consoleToAreaWidthRatio = 0.9,
        consoleToAreaHeightRatio = 0.5;

    var sizeEditor = function () {

        var defaultHeight = $('#consolearea').height() * consoleToAreaHeightRatio;
        $('#resizable').height(Lockr.get('editorHeight', defaultHeight))

        editor.resize();

        /*$("#resizable").resizable({
            resize: function (event, ui) {
                //Lockr.set('editorWidth', $('#editor').width());
                Lockr.set('editorHeight', $('#editor').height());

                editor.resize();
            }
        });*/
    };

    var styleEditor = function () {
        window.editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
        (function () {
            editor.session.setMode("ace/mode/groovy");
        }());
    };

    var showEditor = function () {
        var lastScript = Lockr.get('lastScript');
        if (lastScript) {
            editor.setValue(lastScript, -1);
        }

        $('#consolearea').show();
        $('#formarea').hide();
    };

    var hideAllPanels = function () {
        $(".info-error, .info-result, .info-output, .info-meta").hide();
    };

    $(document).ready(function () {
        console.log("Loading editor... ");
        hideAllPanels();
        styleEditor();
        sizeEditor();
        showEditor();
        console.log("Loading editor finished.");
    });
})
(Granite.$, $(document), Granite.author);
/*
 AemScriptConsole.localStorage = function () {

 }

 $(function () {


 AemScriptConsole.initEditor();
 AemScriptConsole.initTheme();
 AemScriptConsole.initApplicationToolbar();
 AemScriptConsole.initEditorToolbarActions();
 AemScriptConsole.initFormToolbarActions();

 });*/