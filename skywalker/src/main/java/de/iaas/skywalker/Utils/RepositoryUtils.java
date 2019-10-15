package de.iaas.skywalker.Utils;

import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.DeploymentPackages.model.DeploymentPackage;
import de.iaas.skywalker.DeploymentPackages.repository.DeploymentPackageRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.MappingModules.model.MappingModule;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RepositoryUtils {

    private static final String MAPPINGS = "/src/main/resources/mapping.configurations/";
    private static final String TEMPLATES = "/src/main/resources/templates/";

    public void initDeploymentPackageRepository(DeploymentPackageRepository deploymentPackageRepository) throws IOException {
        DeploymentPackage deploymentPackage = new DeploymentPackage();
        deploymentPackage.setId("ThumbnailGeneratorFunction");
        deploymentPackage.setAnalyzed(false);
        deploymentPackage.setDeploymentModel(readFileToString(Paths.get("").toAbsolutePath().toString()
                + "/src/main/resources/packages/thumbnailer/serverless.yml"));
        deploymentPackage.setFunctions(new HashMap<String, String>(){{
            put("UploadHandler", readFileToString(Paths.get("").toAbsolutePath().toString()
                    + "/src/main/resources/packages/thumbnailer/src/main/java/xyz/cmueller/serverless/UploadHandler.java"));
            put("ThumbnailGenerationHandler", readFileToString(Paths.get("").toAbsolutePath().toString()
                    + "/src/main/resources/packages/thumbnailer/src/main/java/xyz/cmueller/serverless/ThumbnailGenerationHandler.java"));
        }});
        deploymentPackageRepository.save(deploymentPackage);
    }

    public void initDeploymentModelRepository(DeploymentModelRepository deploymentModelRepository) throws IOException {
        DeploymentModel thumbnailerModel = new DeploymentModel();
        thumbnailerModel.setName("thumbnail_generation_example.yaml");
        thumbnailerModel.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + TEMPLATES + thumbnailerModel.getName()));
        deploymentModelRepository.save(thumbnailerModel);

        DeploymentModel cronModel = new DeploymentModel();
        cronModel.setName("cron.yaml");
        cronModel.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + TEMPLATES + cronModel.getName()));
        deploymentModelRepository.save(cronModel);

        DeploymentModel eventProcessingModel = new DeploymentModel();
        eventProcessingModel.setName("event_processing.yaml");
        eventProcessingModel.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + TEMPLATES + eventProcessingModel.getName()));
        deploymentModelRepository.save(eventProcessingModel);

        DeploymentModel snsModel = new DeploymentModel();
        snsModel.setName("sns.yaml");
        snsModel.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + TEMPLATES + snsModel.getName()));
        deploymentModelRepository.save(snsModel);

        DeploymentModel serverLessFrameworkTemplateModel = new DeploymentModel();
        serverLessFrameworkTemplateModel.setName("serverless.yaml");
        serverLessFrameworkTemplateModel.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + TEMPLATES + serverLessFrameworkTemplateModel.getName()));
        deploymentModelRepository.save(serverLessFrameworkTemplateModel);
    }

    public void initMappingModuleRepository(MappingModuleRepository mappingModuleRepository) throws IOException {
        MappingModule mappingModule = new MappingModule();
        mappingModule.setName("rule_serverless_v2.yaml");
        mappingModule.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + MAPPINGS + mappingModule.getName()));
        mappingModuleRepository.save(mappingModule);

        MappingModule readmeModule = new MappingModule();
        readmeModule.setName("rule_manual.yaml");
        readmeModule.setBody(readFileToString(
                Paths.get("").toAbsolutePath().toString() + MAPPINGS + readmeModule.getName()));
        mappingModuleRepository.save(readmeModule);
    }

    public void initServiceMappingRepository(ServiceMappingRepository serviceMappingRepository) {
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "http",
                        "aws",
                        "http",
                        Arrays.asList("path", "authorizer", "method", "cors", "private")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "http",
                        "azure",
                        "http",
                        Arrays.asList("route", "authLevel", "methods")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "storage",
                        "aws",
                        "s3",
                        Arrays.asList("bucket", "event", "rules")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "storage",
                        "azure",
                        "blob",
                        Arrays.asList("path", "connection")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "schedule",
                        "aws",
                        "schedule",
                        Arrays.asList("name", "description", "rate", "cron")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "schedule",
                        "azure",
                        "timer",
                        Arrays.asList("schedule", "runOnStartup", "useMonitor")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "stream",
                        "aws",
                        "stream",
                        Arrays.asList("arn", "batchSize", "startingPosition")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "stream",
                        "azure",
                        "eventHub",
                        Arrays.asList("path", "eventHubName", "consumerGroup", "connection")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "point2point",
                        "aws",
                        "sqs",
                        Arrays.asList("arn", "batchSize")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "point2point",
                        "azure",
                        "queue",
                        Arrays.asList("queueName", "consumerGroup", "connection")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "point2point",
                        "azure",
                        "serviceBus",
                        Arrays.asList("queueName", "accessRights", "connection")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "pubsub",
                        "azure",
                        "eventGrid",
                        Arrays.asList("topic")
                )
        );
        serviceMappingRepository.save(
                new EventSourceMapping(
                        "pubsub",
                        "aws",
                        "sns",
                        Arrays.asList("topic", "FilterPolicy", "SqsSubscription", "Region")
                )
        );
    }

    public void initServicePropertyMappingRepository(ServicePropertyMappingRepository servicePropertyMappingRepository) {
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "http",
                        new HashMap<String, List<String>>() {{
                            put("path", Arrays.asList("path", "route"));
                            put("methods", Arrays.asList("method", "methods"));
                            put("rules", Arrays.asList("rules"));
                            put("auth", Arrays.asList("authorizer", "authLevel"));
                            put("cors", Arrays.asList("cors"));
                            put("endpointConfig", Arrays.asList("private"));
                        }}
                )
        );
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "schedule",
                        new HashMap<String, List<String>>() {{
                            put("schedule", Arrays.asList("rate", "schedule", "cron"));
                            put("onBootUp", Arrays.asList("runOnStartup"));
                            put("monitoring", Arrays.asList("useMonitor"));
                        }}
                )
        );
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "stream",
                        new HashMap<String, List<String>>() {{
                            put("resourceId", Arrays.asList("arn", "path", "eventHubName"));
                            put("batchSize", Arrays.asList("batchSize"));
                            put("startingPosition", Arrays.asList("startingPosition"));
                            put("consumerGroup", Arrays.asList("consumerGroup"));
                        }}
                )
        );
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "point2point",
                        new HashMap<String, List<String>>() {{
                            put("resourceId", Arrays.asList("arn", "queueName", "queueName"));
                            put("batchSize", Arrays.asList("batchSize"));
                        }}
                )
        );
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "storage",
                        new HashMap<String, List<String>>() {{
                            put("resourceId", Arrays.asList("bucket", "path"));
                            put("events", Arrays.asList("event"));
                            put("rules", Arrays.asList("rules"));
                        }}
                )
        );
        servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        "pubsub",
                        new HashMap<String, List<String>>() {{
                            put("resourceId", Arrays.asList("topic", "Topic"));
                            put("filter", Arrays.asList("FilterPolicy"));
                            put("queueSubscription", Arrays.asList("SqsSubscription"));
                            put("region", Arrays.asList("Region", "region"));
                        }}
                )
        );
    }

    public String readFileToString(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
        return sb.toString();
    }
}
