package de.iaas.skywalker.mapper;

import de.iaas.skywalker.models.ServiceMapping;
import de.iaas.skywalker.repository.ServiceMappingRepository;

import java.util.*;

public class PlatformSpecificModel {
    private Map<String, Map<String, Object>> templateMap;
    private Map<String, Map<String, List<String>>> mapOfApplicationPropsSimplified = new HashMap<>();
    Map<String, List<String>> pam = new HashMap<>();
    private ServiceMappingRepository serviceMappingRepository;

    public PlatformSpecificModel(Map<String, Map<String, Object>> templateMap, ServiceMappingRepository serviceMappingRepository) {
        this.templateMap = templateMap;
        this.serviceMappingRepository = serviceMappingRepository;
    }

    public void mapEntryToStringList(String entry) {
        Map<String, List<String>> filterConfig = new HashMap<>();
        Iterator it = this.templateMap.get(entry).entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairKey = (String) pair.getKey();
            List<String> properties = new ArrayList<>();
            Map<String, Object> prop = (Map<String, Object>) pair.getValue();
            Iterator itp = prop.entrySet().iterator();
            while(itp.hasNext()) {
                Map.Entry propPair = (Map.Entry) itp.next();
                String propConfig = (String) propPair.getKey();
                String propConfigParams = propPair.getValue().toString();
                properties.add(propConfig);
                // Todo: Maybe add the propConfigParams, too? (like: method: "get")
            }
            filterConfig.put(pairKey, properties);
        }
        this.mapOfApplicationPropsSimplified.put(entry, filterConfig);
    }

    public void makePAM() {
        Iterator it = this.mapOfApplicationPropsSimplified.get("EventSources").entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry event = (Map.Entry) it.next();
            String eventName = (String) event.getKey();
            if(!this.serviceMappingRepository.findByProviderResourceId(eventName).isEmpty()) {
                String grid = this.serviceMappingRepository.findByProviderResourceId(eventName).get(0).getGenericResourceId();
                if (grid != null) this.pam.put(grid, (List<String>) event.getValue());
            }
        }
    }
}
