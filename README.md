# skywalker - a framework for stepping over the boundaries of cloud platforms
![skywalker](https://github.com/iaas-splab/skywalker-prototype/blob/master/media/skywalker_icon.PNG)  
Skywalker is a framework to support developers in porting existing FaaS applications from one provider/platform to another.
It comprises an [Angular](https://angular.io/) powered frontend and a [Spring-based backend](https://spring.io/projects/spring-boot) with an interaction via REST API.

## Technical overview
![skywalker](https://github.com/iaas-splab/skywalker-prototype/blob/master/media/system_architecture_generic.PNG)

### :zap: Templates :zap: 
*- Transient for current session - needs to be added again when rebooting server -*
* The Deployment Model Templates view provides a list of available deployment models
* They can be expanded to investigate the body of the deployment model
* Add more deployment model files via `Add`  
    * There are two options:  
        * **Crawling**: Just as a conceptual demonstration of how deployment models can directly be extracted from the
        platform the application is deployed on. Extracted deployment models are stored in the resource layer of the 
        backend. Note that this feature triggers bash scripts, which means, it can only be used on UNIX-based systems.
        Further, the corresponding CLI tool, e.g., `awscli` must be installed and configured with the needed access rights
        for grabbing this information
        * **Template**: Upload an existing deployment model file to the server. Currently works for all YAML formatted 
        modeling files. Clicking `Submit` will navigate automatically to the Deployment Model Template list view again.
* Delete all existing models via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all models, they will be available again when restarting the server.
    
### :zap: Modules :zap: 
*- Transient for current session - needs to be added again when rebooting server -*
* The Mapping Modules view provides a list of available mapping modules
* They can be expanded to investigate the body of the mapping module
* Add more mapping modules via `Add`  
    * There are two options:  
        * Write the mapping module directly in the web browser
        * Upload an existing mapping module file to the server. Currently works for mapping modules in YAML format.
        Clicking `Submit` will navigate automatically to the Mapping Modules list view again.
* Delete all existing modules via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all models, they will be available again when restarting the server.
        
### :zap: Models :zap: 
*- Persisted even when rebooting the server -*
* The Generic Application Models view provides a list of available generic application models
* They can be expanded and investigated for `Event sources`, `Invoked services` and `Functions`  
    * **Event sources**: Shows all declared event sources in form of generic resource identifiers in the current 
    application and the names of their properties, also mapped to generic identifiers.    
    * **Invoked services**: Shows all declared invoked services in the current application. Currently, we did not focus 
    too much on generifying them.
    * **Functions**: Shows all declared functions in the current application. Also lists all found properties of the functions.
* Evaluate portability to another FaaS platform by clicking `Evaluate` in the corresponding application model's field  
* Delete all existing models via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all table data, it will be available again when restarting the server.

### :zap: Evaluation :zap: 
* When the evaluation is triggered, the current application model is analyzed with a given target platform
* Currently, this automatically is triggered to compare the model with Azure's platform
* For each generic event source in the application model, the target platforms supported event sources are compared with 
them  
    * The comparison is performed based on the event sources' generic properties and yield a coverage score in percent
    * The overall coverage score for the application is displayed at the top labeled as *Portability to <target-platform-name>: XX%*
* Click `Translate` to generate a mostly equivalent deployment model template for the target provider  
    * Clicking this button will redirect to the list view of deployment models
    * There the current deployment model's name and the target name as prefix identifies the newly generated template
    * Note that this currently is in its proof-of-concept phase and only works for deployment models in YAML format of
    Serverless Framework deployment models of AWS Lambda-based applications and generates a boilerplate template of its
    counterpart for Azure Functions in the same format.

### :zap: ServiceRepo :zap: 
*- Transient for current session - needs to be added again when rebooting server -*
* The Service Mapping Repository View provides the table of generic event source mappings
* Add more mapping rules by clicking `Add`  
    * For creating a new mapping rule, the following fields must be filled out:  
        * *Generic Resource ID*: enter the generic identifier that is used as the event source category in order to map this
        event source to another from a different provider with the same generic resource ID
        * *Provider*: enter the provider for which the event source is supported
        * *Provider Resource ID*: enter the provider-specific name of the event source (e.g. S3 or SNS)
        * *List of properties*: enter a list of properties which are required or supported for this provider-specific 
        event source offering
    * Click `Submit` to get redirected to the table view again and check if the new entry has been added at the bottom of the table
* Delete all existing mappings via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all table data, it will be available again when restarting the server.

### :zap: PropertyRepo :zap: 
*- Transient for current session - needs to be added again when rebooting server -*
* The Property Mapping Repository View provides the table of generic event source property mappings
* Add more mapping rules by clicking `Add`  
    * For creating a new mapping rule, the following fields must be filled out:  
        * *Generic Resource ID*: enter the generic identifier that is used as the event source category
        * `Add Generic Property with mapping`: Follow the instructions to add a mapping of provider-specific identifiers
        which correspond to the same generic property identifier, e.g., **storageId:bucket,location,instance**
    * Click `Submit` to get redirected to the table view again and check if the new entry has been added at the bottom of the table
* Delete all existing mappings via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all table data, it will be available again when restarting the server.

### :zap: Packages :zap: 
* The Deployment Packages View provides the list of all available deployment packages
* They can be expanded and investigated for `Deployment Model` and `Functions`  
    * **Deployment Model**:  
        * Investigate the body of the deployment model file.
        * Click `Add to templates` to add the current deployment model to the list of Deployment Model Templates
    * **Functions**: Displays a list of functions  
        * Investigate the function's body by expanding the form element
        * Click `Analyze functions` to trigger the Code Analysis feature  
            * The deployment package is replaced with annotated functions
            * The deployment package is flagged as *analyzed* so that the annotated functions cannot be annotated again by mistake
            * The functions are analyzed for lines of code with potential usage of provider-specific libraries or API calls
            * Currently works for functions, written in Java for AWS Lambda
* Currently only available as demonstration of the feature.
* The example deployment package is reloaded every time the server reboots
* Delete all existing packages via `Reset all`.  
    * Note that there is a set of examples which will be automatically loaded every time the server reboots. So if you 
    delete all models, they will be available again when restarting the server.
    
## Running the tests
* Simply run the unit test by starting them in your IDE
* For example, in IntelliJ IDEA:  
    * Select test class
    * Right click > "Run TEST... with Coverage"


## Getting started

### Prerequisites
- **mongod** 4.2.0
- **Java** 8
- **JavaParser** 3.15.0
- **snakeyaml** 1.23
- **Spring Boot** 2.1.7.RELEASE
- **Angular CLI** 7.1.4
- **Node** 10.16.3
- **Angular** 7.1.4

#### Development Environments
While developing this system, we used the following IDEs/tools:
* **Visual Studio Code** 1.24.1
    * *Shell* 1.7.12
    * *Node* 7.9.0
* **IntelliJ IDEA** 2018.1.3 (Ultimate Edition)  
  * *JRE* 1.8.0_152-release-1136-b38 amd64  
  * *JVM* OpenJDK 64-Bit Server VM by JetBrains s.r.o  
* **Windows 10 10.0** and **macOS Mojave 10.14**

### Start database server
First, make sure that the MongoDB server is running on port `27017` (standard port for MongoDB) . Start the server by running the following command:  
```bash
    $ mongod --dbpath data/
```
If an error occurs, the database could be corrupted because of several reasons, including unclean shutdowns. In this case the following routine should fix the issue:
```bash
    $ rm data/mongod.lock
    $ mongod --dbpath data/ --repair
    $ mongod --dbpath data/
```

### Run server application
With a running instance of the database, the backend server can be started. Make sure that port `8080` is available. 
#### Using maven
```bash
    $ cd skywalker && mvn install
    $ mvn spring-boot:run
```
#### From an IDE
Typically, any IDE has support for spring boot applications. Just import `skywalker` as a maven project and run it like a simple Java application:  
  > Import -> Existing Maven Projects

### Start the client
Make sure that port `4200` is available, otherwise add the `--port PORT` flag to the following commands:
```bash
    $ cd skywalker-client/ && npm install
    $ ng serve --open --live-reload false
```
or simply via
```bash
    $ npm start
```

The landing page of the skywalker can be accessed on a web browser at  
  > http://localhost:4200/


![skywalker](https://github.com/iaas-splab/skywalker-prototype/blob/master/media/skywalker_landing_page.PNG)

## General notes
#### Preventing live-reload in angular
We highly recommend to start the client with
```bash
    $ ng serve --live-reload false
```
so that the current view which holds temporary data is not reloaded by mistake and consequently leads to a bad experience.

#### Further toolings and CLIs
The listed prerequisites are for the general functionality of the framework
* **[Crawler](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/crawler/aws)** can only be used on UNIX-based systems since it utilizes bash scripts and other dependencies such as `wget`
* Further, it is mandatory that the corresponding CLI is installed and configured in a way that the user rights are sufficient
to access the desired resources (in this case `awscli`)

#### Project structure
The project is divided in a frontend application *skywalker-client* and the server application *skywalker*
For a detailed view of the project structure and to get an idea of how to extend the capabilities of this framework, take
a look at the [project structure docs](https://github.com/iaas-splab/skywalker-prototype/blob/master/docs/project_structure/README.md)

#### Dependency management
If there should be any problem in regards of Maven dependencies, please check them under [skywalker/pom.xml](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/pom.xml).

#### Exclude resources
In order to push the example data for demonstration purposes, we did not exclude the resources folder in .gitignore.
However, we highly recommend to exclude them when testing the framework with malformed example files or similar.

## Governance
Skywalker is a project for a masters thesis at the [Institute of Architecture of Application Systems (IAAS)](https://www.iaas.uni-stuttgart.de/en/) of the University of Stuttgart.
Until 18.10.2019 it will be maintained by [Ayhan Kaplan](https://github.com/kaplanan). After this point of time, the project will most likely developed by research associates of IAAS.

## License
Skywalker is free to use and completely open source under Apache License (Version 2.0). See [License](https://github.com/iaas-splab/skywalker-prototype/blob/master/LICENSE) for more information.
