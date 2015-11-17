# AEM Script console
Using the AEM script console you can easily execute custom scripts in the context of AEM. The console supports the exuction of groovy script while javascript support is planned as well.

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
