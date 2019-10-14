package de.iaas.skywalker.Translator.ServerlessFramework.Azure;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Translator.ServerlessFramework.TemplateGenerator;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AzureTemplateGenerator extends TemplateGenerator {

    private DeploymentModel deploymentModel;
    private ServiceMappingRepository serviceMappingRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;

    private static final List<String> SPECIFIC_DECLARATION_BLOCKS = new ArrayList<String>() {{ add("resources"); }};
    private static final Map<String, String> DEFAULT_PROPERTIES = new HashMap<String, String>() {{
        put("provider", "azure");
        put("location", "West US");
    }};

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
        azureConfig.put("name", DEFAULT_PROPERTIES.get("provider"));
        azureConfig.put("location", DEFAULT_PROPERTIES.get("location"));

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

    private Map<String, Object> translateToProviderSpecificEvent(Map<String, Object> sourceEvent) {
        // find the EventResourceMapping entry in ServiceMappingRepository which relates to the target providers
        // resource mapping for the current event of the source application (sourceEvent).
        String sourceEventName = sourceEvent.keySet().toArray()[0].toString();
        EventSourceMapping sourceEventMapping = this.serviceMappingRepository.findByProviderResourceId(sourceEventName).get(0);
        List<EventSourceMapping> genericResourceMapping = this.serviceMappingRepository.findByGenericResourceId(sourceEventMapping.getGenericResourceId());
        EventSourceMapping azEventSourceMapping = new EventSourceMapping();
        for (EventSourceMapping esm : genericResourceMapping) {
            if (esm.getProvider().equals(DEFAULT_PROPERTIES.get("provider"))) azEventSourceMapping = this.serviceMappingRepository.findById(esm.getId());
        }
        String grid = azEventSourceMapping.getGenericResourceId();
        List<String> azEventProperties = azEventSourceMapping.getServiceProperties();

        // get list of properties for source application
        List<String> sourceProperties = this.getSourcePlatformEventProperties(sourceEvent);

        // workaround for not existing actual point-to-point mapping capabilities of the current state of the repositories
        Map<String, Object> propertiesTemplate = new HashMap<>();
        List<GenericServiceProperty> propertyMappingsForCurrentGrid = this.servicePropertyMappingRepository.findByGenericResourceId(grid);
        for (GenericServiceProperty gsp : propertyMappingsForCurrentGrid) {
            Map<String, List<String>> propertyMapping = gsp.getGenericServicePropertyMap();
            Iterator pmit = propertyMapping.entrySet().iterator();
            while (pmit.hasNext()) {
                Map.Entry propertyLookup = (Map.Entry) pmit.next();
                String sourceGrid = (String) propertyLookup.getKey();
                List<String> providerSpecificEventNames = (List<String>) propertyLookup.getValue();

                // if the source provider-specific event name is in the mapping list of the current generic resource id
                // then we can look up which of the event names in azEventProperties is also in this mapping list and
                // replace the source providers event with this.
                for (String providerEventName : providerSpecificEventNames) {
                    if (sourceProperties.contains(providerEventName)) {
                        for (String azEventProperty : azEventProperties) {
                            if (providerSpecificEventNames.contains(azEventProperty)) {
                                propertiesTemplate.put(azEventProperty, "");
                            }
                        }
                    }
                }
            }
        }
        return propertiesTemplate;
    }

    private Map<String, Object> transformEventsToAzure(Map<String, Object> function) {
        List<Map<String, Object>> translatedEvents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> events = (List<Map<String, Object>>) function.get("events");
        events.forEach(event -> translatedEvents.add(this.translateToProviderSpecificEvent(event)));
        function.put("events", translatedEvents);
        return function;
    }

    public String translateSourceDeploymentModelToTargetProviderTemplate() throws IOException {
        Map<String, Object> body = this.loadContent(this.deploymentModel);
        Map<String, Object> providerConfig = (Map<String, Object>) body.get("provider");

        // transform provider section of the template
        body.put("provider", this.transformToAzureProviderConfig(providerConfig));

        // translate functions section
        Map<String, Object> functions = (Map<String, Object>) body.get("functions");
        Map<String, Object> transformedFunctions = new HashMap<>();
        functions.forEach((fName, fBody) -> {
            transformedFunctions.put(fName, this.transformEventsToAzure((Map<String, Object>) fBody));
        });
        body.put("functions", transformedFunctions);

        // remove declaration blocks of other provider-specific resources, e.g., AWS CloudFormation declarations
        SPECIFIC_DECLARATION_BLOCKS.forEach(excl -> body.remove(excl));

        // dump and return the Hashmap in YAML format
        Yaml yaml = new Yaml();
        return yaml.dump(body);
    }

}
