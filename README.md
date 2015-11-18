# ASFC - AEM Script and Forms Console
Using the AEM script and forms console technical and non technical users can easily execute custom scripts in the context of AEM e.g. to fix or migrate content. 
The console supports the execution of groovy scripts while javascript support is planned as well.
For non technical users unnecessary complexity is hidden behind a form for each scripts. This form is bound to a script and redenered through the ASFCs scriptrunner interface.

![Editor View](https://github.com/thomashartm/aem-script-console/blob/screenshots/pictures/scriptconsole.png "AEM Script Console Editor View")

The console supports the execution of scripts via a predefined form for each script. Therefore the console has an editor view to define the script, a form view to configure the input parameter form and a script runner UI that renders the form and passes the field values to the script.

## Supported scripting languages
Currently the groovy scripting language is supported. The console comes a number of object bindings:
- resolver
- session
- jackrabbitSession
- pageManager
- bundleContext
- queryBuilder

All bindings use the current user's session

## Status
The console is work in progress. The script execution is already working.
Scriptrunner is work in progress.
