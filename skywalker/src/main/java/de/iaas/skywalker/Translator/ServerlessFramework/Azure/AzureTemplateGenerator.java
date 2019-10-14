package de.iaas.skywalker.Translator.ServerlessFramework.Azure;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Translator.ServerlessFramework.TemplateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AzureTemplateGenerator extends TemplateGenerator {

    private DeploymentModel deploymentModel;
    private ServiceMappingRepository serviceMappingRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;

    public AzureTemplateGenerator(DeploymentModel deploymentModel, ServiceMappingRepository serviceMappingRepository, ServicePropertyMappingRepository servicePropertyMappingRepository) {
        this.deploymentModel = deploymentModel;
        this.serviceMappingRepository = serviceMappingRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
    }

    private Map<String, Object> loadContent(DeploymentModel deploymentModel) throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new ByteArrayInputStream(deploymentModel.getBody().getBytes());
        Map<String, Object> templateMap = yaml.load(inputStream);
        inputStream.close();
        return templateMap;
    }

    private Map<String, Object> transformToAzureProviderConfig(Map<String, Object> config) {
        Map<String, Object> azureConfig = new HashMap<>();

        // set azure provider defaults
        azureConfig.put("name", "azure");
        azureConfig.put("location", "West US");

        // find generic provider configuration properties in source config
        azureConfig.put("runtime", config.get("runtime"));
        azureConfig.put("stage", config.get("stage"));
        azureConfig.put("environment", config.get("environment"));

        return azureConfig;
    }

    private List<String> getSourcePlatformEventProperties(Map<String, Object> event) {
        List<String> sourceProperties = new ArrayList<>();
        Iterator it = event.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Map<String, String> value = (Map<String, String>) pair.getValue();
            Iterator propMap = value.entrySet().iterator();
            while (propMap.hasNext()) {
                Map.Entry prop = (Map.Entry) propMap.next();
                String p = (String) prop.getKey();
                String pVal = (String) prop.getValue();
                sourceProperties.add(p);
            }
        }
        return sourceProperties;
    }

    private Map<String, Object> translateToProviderSpecificEvent(Map<String, Object> event) {
        Map<String, Object> propertiesTemplate = new HashMap<>();
        List<String> sourceProperties = this.getSourcePlatformEventProperties(event);
        String sourceEventName = event.keySet().toArray()[0].toString();

        EventSourceMapping sourceEventMapping = this.serviceMappingRepository.findByProviderResourceId(sourceEventName).get(0);
        List<EventSourceMapping> genericResourceMapping = this.serviceMappingRepository.findByGenericResourceId(sourceEventMapping.getGenericResourceId());
        String azureResourceId = "";
        for (EventSourceMapping esm : genericResourceMapping) {
            if (esm.getProvider().equals("azure")) azureResourceId = esm.getId();
        }
        EventSourceMapping esm = this.serviceMappingRepository.findById(azureResourceId);
        String grid = esm.getGenericResourceId();
        List<String> properties = esm.getServiceProperties();

        List<GenericServiceProperty> gspList = this.servicePropertyMappingRepository.findByGenericResourceId(grid);
        for (GenericServiceProperty gsp : gspList) {
            Map<String, List<String>> propertyMapping = gsp.getGenericServicePropertyMap();
            Iterator pmit = propertyMapping.entrySet().iterator();
            while (pmit.hasNext()) {
                Map.Entry propertyLookup = (Map.Entry) pmit.next();
                String sourceGrid = (String) propertyLookup.getKey();
                List<String> gridList = (List<String>) propertyLookup.getValue();
                for (String p : gridList) {
                    if (sourceProperties.contains(p)) {
                        for (String s : properties) {
                            System.out.println("aws: " + p);
                            if (gridList.contains(s)) {
                                System.out.println("azure: " + s);
                                propertiesTemplate.put(s, "");
                            }
                        }
                    }
                }
            }
        }
        return propertiesTemplate;
    }

    private Map<String, Object> transformEventsToAzure(Map<String, Object> function) {
        /*
        * {http={path=upload, method=post}}
        * {s3={bucket=${self:custom.image_bucket_name}, event=s3:ObjectCreated:*}}
        * */
        List<Map<String, Object>> translatedEvents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> events = (List<Map<String, Object>>) function.get("events");
        events.forEach(event -> translatedEvents.add(this.translateToProviderSpecificEvent(event)));
        function.put("events", translatedEvents);
        return function;
    }

    public void translateSourceDeploymentModelToTargetProviderTemplate() throws IOException {
        Map<String, Object> body = this.loadContent(this.deploymentModel);

        // tranform provider section of the template
        Map<String, Object> providerConfig = (Map<String, Object>) body.get("provider");
        Map<String, Object> azureProviderConfig = this.transformToAzureProviderConfig(providerConfig);
        body.put("provider", azureProviderConfig);
//        System.out.println(body.toString());

        // translate functions section
        Map<String, Object> functions = (Map<String, Object>) body.get("functions");
        Map<String, Object> transformedFunctions = new HashMap<>();
        functions.forEach((fName, fBody) -> {
            transformedFunctions.put(fName, this.transformEventsToAzure((Map<String, Object>) fBody));
        });
        body.put("functions", transformedFunctions);

        System.out.println(body.toString());
    }

}
