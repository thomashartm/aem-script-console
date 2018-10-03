(function ($, document, graniteAuthor) {

    const RUN_GROOVYSCRIPT_ENDPOINT = '/bin/nclabs/groovyconsole/execute';

    $(document).on("click", ".execute-script-button", function (element) {
        executeScript(element);
    });

    function encodeQueryData(data) {
        return Object.keys(data).map(function (key) {
            return [key, data[key]].map(encodeURIComponent).join("=");
        }).join("&");
    }

    function executeScript(element) {
        let scriptSource = document.getElementById("groovy-script-source");

        let scriptLocation = scriptSource.dataset.scriptLocation;
        if (!scriptLocation) {
            // no script location to validate
            return;
        }

        let queryParams = getQueryParameters(scriptLocation);
        let url = RUN_GROOVYSCRIPT_ENDPOINT + "?" + encodeQueryData(queryParams);
        let errorMessage;

        $.ajax({
            url,
            type: "GET",
            async: false,
            success: function (data) {
                try {
                    console.log(data);
                    writeScriptExecutionResults(data);
                } catch (err) {
                    errorMessage = Granite.I18n.get("Unexpected error {0}" + err)
                }
            },
            error: function () {
                errorMessage = Granite.I18n.get("Unexpected error while communicating with server");
            }
        });

        if (errorMessage) {
            return errorMessage;
        }
    }

    function getQueryParameters(scriptLocation){
        let queryParams = {
            "location": scriptLocation
        };

        // get all text field values
        let fields = document.querySelectorAll("input.groovyconsole-field");
        for (let i = 0; i < fields.length; i++) {
            let textField = fields[i];
            if (textField) {
                queryParams[textField.name] = textField.value;
            }
        }

        // get radio button values
        let radios = document.querySelectorAll("coral-radio.groovyconsole-radio");
        for (let i = 0; i < radios.length; i++) {
            let radio = radios[i];
            if (radio.checked) {
                queryParams[radio.name] = radio.value;
            }
        }

        return queryParams;
    }

    function writeMessageBox(data) {
        let messageType = data.failed ? "error" : "success";

        let timing = data.executionTime + " ms";
        let message = "";
        if (data.error) {
            message += "<br />";
            message += data.error;
        }

        let alertBox = new Coral.Alert();
        alertBox.set({
            variant: messageType,
            header: {
                innerHTML: timing
            },
            content: {
                innerHTML: message
            }
        });
        return alertBox;
    }

    function createContentElement(headerText, contentText) {
        let contentElement = document.createElement("div");
        let header = document.createElement("h3");
        header.append(headerText);
        contentElement.append(header);
        contentElement.append(contentText);

        return contentElement;
    }

    function writeScriptExecutionResults(data) {

        let resultbox = document.getElementById("groovy-results-panel-el");

        // clear all existing elements first
        while (resultbox.firstChild) {
            resultbox.removeChild(resultbox.firstChild);
        }

        let alertBox = writeMessageBox(data);
        resultbox.append(alertBox);

        if (data.output) {
            resultbox.appendChild(createContentElement("Execution Output", data.output));
        }

        if (data.result) {
            resultbox.appendChild(createContentElement("Execution Result", data.result));
        }
    }

})(jQuery, document, Granite.author);
