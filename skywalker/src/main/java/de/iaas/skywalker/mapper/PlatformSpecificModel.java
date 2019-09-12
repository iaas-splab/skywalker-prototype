package de.iaas.skywalker.mapper;

import java.util.*;

public class PlatformSpecificModel {
    private Map<String, Map<String, Object>> templateMap;
    private Map<String, Map<String, List<String>>> mapOfApplicationPropsSimplified = new HashMap<>();

    public PlatformSpecificModel(Map<String, Map<String, Object>> templateMap) {
        this.templateMap = templateMap;
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

}
