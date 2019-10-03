package de.iaas.skywalker.evaluation;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EvaluationHelper {

    private Map<String, List<String>> sourceApplicationEventSources;
    private DecimalFormat format = new DecimalFormat("00.##");

    public EvaluationHelper(Map<String, List<String>> sourceApplicationEventSources) {
        this.sourceApplicationEventSources = sourceApplicationEventSources;
    }

    public Map<String, List<String>> getSourceApplicationEventSources() {
        return sourceApplicationEventSources;
    }

    public void setSourceApplicationEventSources(Map<String, List<String>> sourceApplicationEventSources) {
        this.sourceApplicationEventSources = sourceApplicationEventSources;
    }

    private double evaluateServiceSimilarity(List<String> sourceProperties, List<String> targetProperties) {
        double propertyWeight = 1.0 / sourceProperties.size();
        double coverage = 0.0;
        for(String sProp : sourceProperties) {
            if(targetProperties.stream().anyMatch(tProp -> tProp.equals(sProp))) coverage += propertyWeight;
        }
        return coverage;
    }

    private Double makeDoubleWithTwoDigitsButveryHacky(double number) {
        String doubleString = String.valueOf(number);
        return Double.valueOf(((doubleString.length() >= 4) ? doubleString.substring(0,4) : doubleString)
                .replace(",", "."));
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

    public double evaluatePlatformCandidateCoverageScore(Map<String, List<String>> candidatePlatformServices) {
        double serviceWeight = 1.0 / this.sourceApplicationEventSources.size();
        double platformCoverage = 0.0;
        Iterator sourceServices = this.sourceApplicationEventSources.entrySet().iterator();
        while(sourceServices.hasNext()) {
            Map.Entry sService = (Map.Entry) sourceServices.next();
            String sGRID = (String) sService.getKey();
            List<String> sProperties = (List<String>) sService.getValue();
            if(candidatePlatformServices.containsKey(sGRID)) {
                platformCoverage += serviceWeight * this.evaluateServiceSimilarity(sProperties, candidatePlatformServices.get(sGRID));
            }
        }
        return platformCoverage;
    }

    public Map<String, Double> evaluatePropertyCoverageScores(Map<String, List<String>> candidatePlatformEventSources) {
        Map<String, Double> serviceCoverageScores = new HashMap<>();
        double serviceWeight = 1.0 / this.sourceApplicationEventSources.size();

        Iterator sourceServices = this.sourceApplicationEventSources.entrySet().iterator();
        while (sourceServices.hasNext()) {
            Map.Entry sService = (Map.Entry) sourceServices.next();
            String sGRID = (String) sService.getKey();
            List<String> sProperties = (List<String>) sService.getValue();
            if (candidatePlatformEventSources.containsKey(sGRID)) {
                double serviceSimilarty = this.evaluateServiceSimilarity(sProperties, candidatePlatformEventSources.get(sGRID));
                serviceSimilarty = this.makeDoubleWithTwoDigitsButveryHacky(serviceSimilarty);
                serviceCoverageScores.put(sGRID, serviceSimilarty);
            }
        }
        return serviceCoverageScores;
    }

    public Map<String, List<Map<String, String>>> getPlatformCandidateEventCoverageModel(Map<String, List<String>> candidatePlatformEventSources) {
        Map<String, List<Map<String, String>>> cutSetServices = new HashMap<>();

        Iterator sourceServices = this.sourceApplicationEventSources.entrySet().iterator();
        while(sourceServices.hasNext()) {
            List<Map<String, String>> propCoverageList = new ArrayList<>();
            Map.Entry sService = (Map.Entry) sourceServices.next();
            String sGRID = (String) sService.getKey();
            List<String> sProperties = (List<String>) sService.getValue();
            if(candidatePlatformEventSources.containsKey(sGRID)) {
                cutSetServices.put(
                        sGRID,
                        getServicePropertyCoverage(sProperties, candidatePlatformEventSources.get(sGRID))
                );
            } else {
                cutSetServices.put(
                        sGRID,
                        sProperties.stream().map(x -> new HashMap<String, String>(){{put(x, "-");}}).collect(Collectors.toList())
                );
            }
        }
        return cutSetServices;
    }
}
