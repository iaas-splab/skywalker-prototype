package de.iaas.skywalker.Translator.ServerlessFramework.Azure;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.MappingModules.util.DeploymentModelMapper;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Translator.ServerlessFramework.TemplateGenerator;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    private Map<String, Object> transformToAzureProviderConfig(Map<String, Object> config) {
        return new HashMap<String, Object>() {{
            put("name", DEFAULT_PROPERTIES.get("provider"));             // Set azure provider defaults
            put("location", DEFAULT_PROPERTIES.get("location"));
            put("runtime", config.get("runtime"));                       // Find generic provider configuration
            put("stage", config.get("stage"));                           // properties in source config
            put("environment", config.get("environment"));
        }};
    }

    /**
     * Get a list of properties for the current event type at the source applications platform
     * @param sourceApplicationEvent Event map to iterate through
     * @return List of property names for the current event mapping
     */
    private List<String> getSourcePlatformEventProperties(Map<String, Object> sourceApplicationEvent) {
        try {
            Map.Entry event = sourceApplicationEvent.entrySet().iterator().next();
            Map<String, String> properties = (Map<String, String>) event.getValue();
            return new ArrayList<String>(){{
                Iterator propertyMap = properties.entrySet().iterator();
                while (propertyMap.hasNext()) {
                    Map.Entry property = (Map.Entry) propertyMap.next();
                    String pName = (String) property.getKey();
                    add(pName);
                }
            }};
        } catch (ClassCastException c) {
            return new ArrayList<>();
        }
    }

    /**
     * Find the EventSourceMapping entry in the ServiceMappingRepository for Azure by first finding the generic resource
     * id (GRID) of the current source application event and subsequently use this GRID to find the entry for Azure.
     * @param sourceApplicationEvent Map of the source applications current event type and its properties
     * @return EventSourceMapping of Azure for the current event type
     */
    private EventSourceMapping getAzureEventSourceMappingForCurrentEvent(Map<String, Object> sourceApplicationEvent) {
        String sourceAppEventName = sourceApplicationEvent.keySet().toArray()[0].toString();
        EventSourceMapping sourceEventMapping = this.serviceMappingRepository.findByProviderResourceId(sourceAppEventName).get(0);
        List<EventSourceMapping> genericResourceMapping =
                this.serviceMappingRepository.findByGenericResourceId(sourceEventMapping.getGenericResourceId());
        for (EventSourceMapping esm : genericResourceMapping) {
            if (esm.getProvider().equals(DEFAULT_PROPERTIES.get("provider"))) {
                return this.serviceMappingRepository.findById(esm.getId());
            }
        }
        return null;
    }

    private boolean hasIntersection(List<String> listA, List<String> listB) {
        return !listA
                .stream()
                .filter(listB::contains)
                .collect(Collectors.toSet())
                .isEmpty();
    }

    /**
     * Find the corresponding event properties at the target provider by utilizing the source application's properties
     * and checking for generic property mapping repo entries in which both property lists are contained.
     * @param sourceApplicationEvent Event map of the source applications current event config
     * @return Boilerplate for the mapped event at the target provider with empty property values.
     */
    private Map<String, Object> translateToProviderSpecificEvent(Map<String, Object> sourceApplicationEvent) {
        EventSourceMapping azEventSourceMapping = this.getAzureEventSourceMappingForCurrentEvent(sourceApplicationEvent);
        String azGRID = azEventSourceMapping.getGenericResourceId();
        List<String> azEventProperties = azEventSourceMapping.getServiceProperties();

        List<String> sourceAppEventProperties = this.getSourcePlatformEventProperties(sourceApplicationEvent);

        GenericServiceProperty azEventPropertyMapping = this.servicePropertyMappingRepository.findByGenericResourceId(azGRID).iterator().next();
        Map<String, List<String>> providerPropertyMap = azEventPropertyMapping.getGenericServicePropertyMap();
        Iterator propertyMappingLookups = providerPropertyMap.entrySet().iterator();
        Map<String, Object> propertiesTemplate = new HashMap<>();
        while (propertyMappingLookups.hasNext()) {
            Map.Entry propertyLookup = (Map.Entry) propertyMappingLookups.next();
            List<String> assignableProviderSpecificProperties = (List<String>) propertyLookup.getValue();
            /* if the event's assignable provider specific properties for the current generic property type contains
             * one of the source application properties, return the property in azEventProperties which is also in this
             * list and put it in the map of properties for the boilerplate template. */
            if (this.hasIntersection(assignableProviderSpecificProperties, sourceAppEventProperties)
                    && this.hasIntersection(assignableProviderSpecificProperties, azEventProperties)) {
                String assignableAzProperty = assignableProviderSpecificProperties
                        .stream()
                        .filter(azEventProperties::contains)
                        .collect(Collectors.toSet())
                        .iterator()
                        .next();
                propertiesTemplate.put(assignableAzProperty, "");
            }
        }
        Map<String, Object> eventTemplate = new HashMap<String, Object>(){{
            put(azEventSourceMapping.getProviderResourceId(), propertiesTemplate);
        }};
        return eventTemplate;
    }

    private Map<String, Object> transformEventsToAzure(Map<String, Object> function) {
        List<Map<String, Object>> translatedEvents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> events = (List<Map<String, Object>>) function.get("events");
        events.forEach(event -> translatedEvents.add(this.translateToProviderSpecificEvent(event)));
        function.put("events", translatedEvents);
        return function;
    }

    /**
     * Generation of a boilerplate template for Azure Functions template in format of a Serverless Framework YAML file.
     * @return YAML formatted string which represents the target platform deployment model's body.
     * @throws IOException
     */
    public String translateSourceDeploymentModelToTargetProviderTemplate() throws IOException {
        DeploymentModelMapper dm = new DeploymentModelMapper();
        Map<String, Object> body = dm.load_deployment_model(this.deploymentModel);
        Map<String, Object> providerConfig = (Map<String, Object>) body.get("provider");

        // Transform provider section of the template
        body.put("provider", this.transformToAzureProviderConfig(providerConfig));

        // Translate functions section
        Map<String, Object> functions = (Map<String, Object>) body.get("functions");
        Map<String, Object> transformedFunctions = new HashMap<>();
        functions.forEach((fName, fBody)
                -> transformedFunctions.put(fName, this.transformEventsToAzure((Map<String, Object>) fBody)));
        body.put("functions", transformedFunctions);

        // Remove declaration blocks of other provider-specific resources, e.g., AWS CloudFormation declarations
        SPECIFIC_DECLARATION_BLOCKS.forEach(excl -> body.remove(excl));

        // Dump and return the Hashmap in YAML format
        return new Yaml().dump(body);
    }

}
