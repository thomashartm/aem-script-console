# AEM  Groovy Script Console

![Editor View](https://travis-ci.org/thomashartm/aem-script-console.svg?branch=master "Travis CI")

With the AEM script console technical and non technical users can easily execute custom groovy scripts in the context of AEM e.g. to fix or migrate content. 

![Editor View](https://github.com/thomashartm/aem-script-console/blob/screenshots/pictures/script-console-overview.png "AEM Script Console Overview")

The console supports the execution of stored or submitted groovy scripts. 
Therefore the console has an editor view to define the script and a script runner UI that allows to start a stored script without having to deal with an editng interface.

![Editor View](https://github.com/thomashartm/aem-script-console/blob/screenshots/pictures/script-console-editor.png "AEM Script Console Editor")


## Supported scripting languages
Currently the groovy scripting language is supported. 
The console comes a number of object bindings:
- resolver
- session
- jackrabbitSession
- pageManager
- bundleContext
- queryBuilder

All bindings use the current user's session or a service user if configured for a script.
The following closure bindings are supported:

- getNode
- getResource
- getPage

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

## Requirements
AEM 6.3 is required and 6.4 recommended.

## License
The software is licensed under the Apache 2.0 License see the attached LICENSE file or 
http://www.apache.org/licenses/LICENSE-2.0.html for details.
