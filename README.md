# ASFC - AEM Script and Forms Console
Using the AEM script and forms console technical and non technical users can easily execute custom scripts in the context of AEM e.g. to fix or migrate content. 

![Editor View](https://github.com/thomashartm/aem-script-console/blob/screenshots/pictures/aem-script-console-2.png "AEM Script Console Editor View")

The console supports the execution of groovy scripts while javascript support is planned as well.

The console supports the execution of scripts via a predefined form for each script. 
Therefore the console has an editor view to define the script, a form view to configure the input parameter form and a script runner UI that renders the form and passes the field values to the script.

For non technical users, complexity is hidden behind such a form. This form is bound to a script and rendered through the ASFCs scriptrunner interface.


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
The console is work in progress. The script execution is already working while the forms interface currently work in progress.

## How to build and deploy
Clone the project the enter the project root folder and execute 
```
mvn clean install 
```

To deploy the project just add the autoInstallPackage profile.
```
mvn clean install -PautoInstallPackage
```

##License
The software is licensed under the Apache 2.0 License see the attached LICENSE file orhttp://www.apache.org/licenses/LICENSE-2.0.html for details.
