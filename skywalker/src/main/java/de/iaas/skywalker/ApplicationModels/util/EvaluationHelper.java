package de.iaas.skywalker.ApplicationModels.util;

import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EvaluationHelper {

    private Map<String, List<String>> sourceApplicationEvents;
    private DecimalFormat format = new DecimalFormat("00.##");

    public EvaluationHelper(Map<String, List<String>> sourceApplicationEvents) {
        this.sourceApplicationEvents = sourceApplicationEvents;
    }

    public Map<String, List<String>> getSourceApplicationEvents() {
        return sourceApplicationEvents;
    }

    public void setSourceApplicationEvents(Map<String, List<String>> sourceApplicationEvents) {
        this.sourceApplicationEvents = sourceApplicationEvents;
    }

    /**
     * Compares the set of properties for one specific event type for both source application and target platform and
     * returns the relative coverage.
     *
     * @param sourceAppEventProperties Properties of the current event type for the source application
     * @param targetPlatformEventProperties Properties of the current event type at the event source of the target platform
     * @return The relative similarity score of the event with respect to the coverage of available event properties.
     */
    private double evaluateEventSimilarity(List<String> sourceAppEventProperties, List<String> targetPlatformEventProperties) {
        double weight = 1.0 / sourceAppEventProperties.size();
        double coverage = 0.0;
        for(String sProp : sourceAppEventProperties) {
            if(targetPlatformEventProperties.stream().anyMatch(tProp -> tProp.equals(sProp))) coverage += weight;
        }
        return coverage;
    }

    private List<Map<String, String>> getServicePropertyCoverage(List<String> sourceProperties, List<String> targetProperties) {
        List<Map<String, String>> propertyCoverageList = new ArrayList<>();
        for (String sProp : sourceProperties) {
            propertyCoverageList.add(new HashMap<String, String>(){{
                if(targetProperties.stream().anyMatch(tProp -> tProp.equals(sProp))) put(sProp, sProp);
                else put(sProp, "-");
            }});
        }
        return propertyCoverageList;
    }

    /**
     * Compares the event sources in the source application with those available for the selected target platform and
     * returns a score which represents the relative coverage of their similarity.
     *
     * @param targetPlatformEvents Map of generic resources each with a list of generic properties which are
     *                             applicable for the selected target platform.
     *
     * @return The relative similarity score of the events in the source application and the target platform with respect
     * to their properties.
     */
    public double evaluateTargetPlatformCoverageScore(Map<String, List<String>> targetPlatformEvents) {
        double weight = 1.0 / this.sourceApplicationEvents.size();
        double coverage = 0.0;
        Iterator sEvents = this.sourceApplicationEvents.entrySet().iterator();
        while(sEvents.hasNext()) {
            Map.Entry sEvent = (Map.Entry) sEvents.next();
            String sGRID = (String) sEvent.getKey();
            List<String> sProperties = (List<String>) sEvent.getValue();
            if(targetPlatformEvents.containsKey(sGRID)) {
                coverage += weight * this.evaluateEventSimilarity(sProperties, targetPlatformEvents.get(sGRID));
            }
        }
        return coverage;
    }

    /**
     * Compares the event sources in the source application with those available for the selected target platform and
     * returns a map of event types and their coverage scores with respect to their properties for each event type.
     *
     * @param targetPlatformEvents Map of generic resources each with a list of generic properties which are
     *                             applicable for the selected target platform.
     * @return Map of coverage scores for each event type that gets compared on the source application and target platform
     */
    public Map<String, Double> evaluteEventCoverageScores(Map<String, List<String>> targetPlatformEvents) {
        Map<String, Double> eventCoverageScores = new HashMap<>();
        double weight = 1.0 / this.sourceApplicationEvents.size();

        Iterator sEvents = this.sourceApplicationEvents.entrySet().iterator();
        while (sEvents.hasNext()) {
            Map.Entry sEvent = (Map.Entry) sEvents.next();
            String sGRID = (String) sEvent.getKey();
            List<String> sProperties = (List<String>) sEvent.getValue();
            if (targetPlatformEvents.containsKey(sGRID)) {
                double eventSimilarity = this.evaluateEventSimilarity(sProperties, targetPlatformEvents.get(sGRID));
                eventCoverageScores.put(sGRID, eventSimilarity);
            } else {
                eventCoverageScores.put(sGRID, 0.0);
            }
        }
        return eventCoverageScores;
    }

    public Map<String, List<String>> getTranslatedTargetModel(Map<String, List<Map<String, String>>> coverageModel, ServiceMappingRepository repository, String provider) {
        Map<String, List<String>> targetEventSources = new HashMap<String, List<String>>();
        Iterator coverageModelIt = coverageModel.entrySet().iterator();
        while(coverageModelIt.hasNext()) {
            Map.Entry coverageModelEntry = (Map.Entry) coverageModelIt.next();
            List<Map<String, String>> value = (List<Map<String, String>>) coverageModelEntry.getValue();
            for (Map<String, String> prop : value) {
                Iterator iti = prop.entrySet().iterator();
                while(iti.hasNext()) {
                    Map.Entry pair = (Map.Entry) iti.next();
                    if(!pair.getValue().equals("-")) {
                        List<EventSourceMapping> esm = repository.findByGenericResourceId(coverageModelEntry.getKey().toString());
                        for (EventSourceMapping e : esm) {
                            if (e.getProvider().equals(provider)) {
                                targetEventSources.put(e.getProviderResourceId(), e.getServiceProperties());
                            }
                        }
                    }
                }
            }
        }
        return targetEventSources;
    }

    /**
     * Compares the event sources in the source application with those available for the selected target platform and
     * returns a map describing their similarities.
     *
     * @param targetPlatformEvents Map of generic resources each with a list of generic properties which are
     *                             applicable for the selected target platform.
     *
     * @return Map of generic resources each with a list of mappings in which the generic properties of the source
     * application event are mapped to the same property of the target platform, if available. Otherwise, the source
     * property is mapped to "-" to illustrate that there is no counterpart of the property at the event source of the
     * target platform.
     */
    public Map<String, List<Map<String, String>>> getPlatformCandidateEventCoverageModel(Map<String, List<String>> targetPlatformEvents) {
        Map<String, List<Map<String, String>>> eventSourceCoverageMap = new HashMap<>();

        Iterator sourceApplicationEvents = this.sourceApplicationEvents.entrySet().iterator();
        while(sourceApplicationEvents.hasNext()) {
            List<Map<String, String>> propCoverageList = new ArrayList<>();
            Map.Entry sEvent = (Map.Entry) sourceApplicationEvents.next();
            String sGRID = (String) sEvent.getKey();
            List<String> sProperties = (List<String>) sEvent.getValue();
            if(targetPlatformEvents.containsKey(sGRID)) {
                eventSourceCoverageMap.put(
                        sGRID,
                        getServicePropertyCoverage(sProperties, targetPlatformEvents.get(sGRID))
                );
            } else {
                eventSourceCoverageMap.put(
                        sGRID,
                        sProperties.stream().map(x -> new HashMap<String, String>(){{put(x, "-");}}).collect(Collectors.toList())
                );
            }
        }
        return eventSourceCoverageMap;
    }
}
