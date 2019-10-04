package de.iaas.skywalker.MappingModules.util;

import de.iaas.skywalker.MappingModules.model.ApplicationProperties;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;

import java.util.*;

public class ModelMappingUtils {

    public ModelMappingUtils() {}

    private Map<String, List<String>> iterateAppProperty(Iterator it) {
        Map<String, List<String>> appPropConfig = new HashMap<>();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            List<String> resourceProperties = new ArrayList<>();
            Iterator pit = ((Map<String, Object>) pair.getValue()).entrySet().iterator();
            while(pit.hasNext()) {
                Map.Entry resourceProperty = (Map.Entry) pit.next();
                String resourcePropertyId = (String) resourceProperty.getKey();
                String resourcePropertyConfiguration = (String) resourceProperty.getKey();
                resourceProperties.add(resourcePropertyId);
            }
            appPropConfig.put((String) pair.getKey(), resourceProperties);
        }
        return appPropConfig;
    }

    private Map<String, List<String>> getInvokedSourcesAtPropertyLevel(ApplicationProperties appProps) {
        Iterator it = appProps.getInvokedServices().entrySet().iterator();
        return this.iterateAppProperty(it);
    }

    private Map<String, List<String>> getEventSourcesAtPropertyLevel(ApplicationProperties appProps) {
        Iterator it = appProps.getEventSources().entrySet().iterator();
        return this.iterateAppProperty(it);
    }

    private Map<String, List<String>> getFunctionsAtPropertyLevel(ApplicationProperties appProps) {
        Iterator it = appProps.getFunctions().entrySet().iterator();
        return this.iterateAppProperty(it);
    }

    public Map<String, Map<String, List<String>>> getAppAtPropertiesLevel(ApplicationProperties appProps) {
        Map<String, Map<String, List<String>>> appPropConfig = new HashMap<>();
        appPropConfig.put("EventSources", this.getEventSourcesAtPropertyLevel(appProps));
        appPropConfig.put("InvokedSources", this.getInvokedSourcesAtPropertyLevel(appProps));
        appPropConfig.put("Function", this.getFunctionsAtPropertyLevel(appProps));
        return appPropConfig;
    }

    public Map<String, List<String>> generifyEventSourceNames(Iterator it, ServiceMappingRepository repository) {
        Map<String, List<String>> genericEventSources = new HashMap<>();
        while(it.hasNext()) {
            Map.Entry eventSource = (Map.Entry) it.next();
            String eventName = (String) eventSource.getKey();
            List<String> properties = (List<String>) eventSource.getValue();
            if(!repository.findByProviderResourceId(eventName).isEmpty()) {
                String grid = repository.findByProviderResourceId(eventName).get(0).getGenericResourceId();
                if (grid != null) genericEventSources.put(grid, properties);
            } else {
                genericEventSources.put(eventName, properties);
            }
        }
        return genericEventSources;
    }

    public Map<String, List<String>> generifyEventSourceProperties(Map<String, List<String>> eventSources, ServicePropertyMappingRepository repository) {
        Iterator it = eventSources.entrySet().iterator();
        Map<String, List<String>> updatedEventSources = eventSources;
        while(it.hasNext()) {
            Map.Entry eventSource = (Map.Entry) it.next();
            String eventName = (String) eventSource.getKey();
            List<String> properties = (List<String>) eventSource.getValue();
            for(String property : properties) {
                if(!repository.findByGenericResourceId(eventName).isEmpty()) {
                    GenericServiceProperty propertyMap = repository.findByGenericResourceId(eventName).get(0);
                    Iterator gpit = propertyMap.getGenericServicePropertyMap().entrySet().iterator();
                    while(gpit.hasNext()) {
                        Map.Entry genericPropMap = (Map.Entry) gpit.next();
                        String genericProperty = (String) genericPropMap.getKey();
                        if(((List<String>)genericPropMap.getValue()).stream().anyMatch(prop -> prop.trim().equals(property))) {
                            properties.set(properties.indexOf(property), genericProperty);
                        }
                    }
                }
            }
            updatedEventSources.put(eventName, properties);
        }
        return updatedEventSources;
    }
}
