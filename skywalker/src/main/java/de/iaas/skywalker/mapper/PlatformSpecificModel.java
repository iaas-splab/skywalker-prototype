package de.iaas.skywalker.mapper;

import de.iaas.skywalker.models.MapEntryBundle;
import de.iaas.skywalker.sqlite.ServiceDBHelper;

import java.util.*;

public class PlatformSpecificModel {
    private Map<String, Map<String, Object>> templateMap;
    private Map<String, Map<String, List<String>>> mapOfApplicationPropsSimplified = new HashMap<>();
    Map<String, List<String>> pam = new HashMap<>();
    private  Map<String, List<String>> GRID_LIST = new HashMap<String, List<String>>() {{
        put("objectstorage", new ArrayList<String>() {{
            add("s3");
            add("blob");
            add("storage");
        }});put("endpoint", new ArrayList<String>() {{
            add("http");
            add("http");
            add("http");
        }});put("pubsub", new ArrayList<String>() {{
            add("sns");
            add("sns");
            add("sns");
        }});put("messagequeueing", new ArrayList<String>() {{
            add("sqs");
            add("sqs");
            add("sqs");
        }});put("eventstreaming", new ArrayList<String>() {{
            add("stream");
            add("stream");
            add("stream");
        }});put("schedule", new ArrayList<String>() {{
            add("schedule");
            add("timer");
            add("schedule");
        }});
    }};

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

    public void makePAM() {
        Iterator it = this.mapOfApplicationPropsSimplified.get("EventSources").entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry event = (Map.Entry) it.next();
            String eventName = (String) event.getKey();
            ServiceDBHelper dbHelper = new ServiceDBHelper();
            if(eventName.equals("S3") || eventName.equals("SNS") || eventName.equals("http")) {
                String grid = dbHelper.gridSelectForPRN(eventName).getKey();
                if (grid != null) this.pam.put(grid, (List<String>) event.getValue());
                System.out.println("one time for " + eventName);
            }
        }
    }
}
