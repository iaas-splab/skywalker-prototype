package de.iaas.skywalker.evaluation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EvaluationHelper {

    private Map<String, List<String>> sourceApplicationServices;

    public EvaluationHelper(Map<String, List<String>> sourceApplicationServices) {
        this.sourceApplicationServices = sourceApplicationServices;
    }

    public Map<String, List<String>> getSourceApplicationServices() {
        return sourceApplicationServices;
    }

    public void setSourceApplicationServices(Map<String, List<String>> sourceApplicationServices) {
        this.sourceApplicationServices = sourceApplicationServices;
    }

    private double evaluateServiceSimilarity(List<String> sourceProperties, List<String> targetProperties) {
        double propertyWeight = 1.0 / sourceProperties.size();
        double coverage = 0.0;
        for(String sProp : sourceProperties) {
            if(targetProperties.stream().anyMatch(tProp -> tProp.equals(sProp))) coverage += propertyWeight;
        }
        return coverage;
    }

    public double compareWithSelectedPlatformCandidate(Map<String, List<String>> candidatePlatformServices) {
        double serviceWeight = 1.0 / this.sourceApplicationServices.size();
        double platformCoverage = 0.0;
        Iterator sourceServices = this.sourceApplicationServices.entrySet().iterator();
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
}
