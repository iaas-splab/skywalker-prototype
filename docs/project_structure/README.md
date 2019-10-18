# Project structure
*Inside skywalker, the Features are bundled with respect to their functionality:*

## Skywalker
* Each service feature is in its own package which contains the following sub-packages: 
    * *[ApplicationModels](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/ApplicationModels), [DeploymentModels](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/DeploymentModels), [MappingModules](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/MappingModules), [DeploymentPackages](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/DeploymentPackages)*  
        * **controller**: RestController which exposes this service to the client application
        * **model**: Entities and Classes needed for this feature
        * **repository**: Contains interface of the RepositoryRestResource for CRUD operations
        * **util** (optional): Contains helper classes for features, such as, [Portability Evaluation](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/ApplicationModels/util/EvaluationHelper.java), [DeploymentModelMapping](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/MappingModules/util/DeploymentModelMapper.java) and 
        [general mapping services](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/MappingModules/util/ModelMappingUtils.java)
    * *[CodeAnalysis](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/CodeAnalysis)*  
        * **[platform-name](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/CodeAnalysis/lambda)**  
            * **[programming-language](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/CodeAnalysis/lambda/javalang)**  
                * **[utils](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/CodeAnalysis/lambda/javalang/utils)**: package for helper classes
                * *[CodeDiscoverer](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/CodeAnalysis/lambda/javalang/CodeDiscoverer.java)*: Class for analyzing the function'ssource code for the current platform in the current
                programming language
    * *[Translator](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/Translator)*  
        * **[format-name](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/Translator/ServerlessFramework)**  
            * **[platform-name](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/Translator/ServerlessFramework/Azure)**: Contains the PlatformGeneration-Adapterclass for the selected platform
            * *[TemplateGenerator](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/Translator/ServerlessFramework/TemplateGenerator.java)*: Class which the PlatformGeneratorsextend from
    * **[Utils](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/java/de/iaas/skywalker/Utils)**  
        * *[ExecUtils](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/Utils/ExecUtils.java)*: Class for integrating features which are notJava-based and executes them from command line executions
        * *[RepositoryUtils](https://github.com/iaas-splab/skywalker-prototype/blob/master/skywalker/src/main/java/de/iaas/skywalker/Utils/RepositoryUtils.java)*: Intializing the repositories with theexample data
* Under **[resources](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/resources)**, the example files are stored which are loaded in*RepositoryUtils*  
    * **[functions](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/resources/functions)**: Here the Java functions are stored
    * **[mapping.configurations](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/resources/mapping.configurations)**: Here the Mapping Module files arestored
    * **[packages](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/resources/packages)**: Here the Deployment Packages are stored
    * **[templates](https://github.com/iaas-splab/skywalker-prototype/tree/master/skywalker/src/main/resources/templates)**: Here the Deployment Model files are stored