

(function(document, Granite, $) {
    "use strict";

    var bCreateLaunchMode = true;
    var ui = $(window).adaptTo("foundation-ui");

    $(function() {

        var ui = $(window).adaptTo("foundation-ui");
        document.addEventListener('DOMContentLoaded', function () {
            var console = $("#dragaction-log");

            // Events generated on drop
            $('#dragaction')[0].addEventListener('coral-dragaction:drop', function (e) {
                console.log('Drop on element with id: ' + e.detail.dropElement.id);
                e.detail.dropElement.style.background = 'beige';
                $(this).css("height", "auto");
            });
        });
    });
})(document, Granite, Granite.$);

