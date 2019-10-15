package de.iaas.skywalker.ApplicationModels.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class PlatformComparisonModel {
    @Id
    private String id;
    private Map<String, List<Map<String, String>>> eventSourceCoverage;
    private String targetPlatform;
    private double platformCoverageScore;
    private Map<String, Double> propertyCoverageScores;
    private String deploymentModelId;

    public PlatformComparisonModel(
            String id,
            String targetPlatform,
            Map<String, List<Map<String, String>>> eventSourceCoverage,
            double platformCoverageScore,
            Map<String, Double> propertyCoverageScores,
            String deploymentModelId) {
        this.id = id;
        this.targetPlatform = targetPlatform;
        this.eventSourceCoverage = eventSourceCoverage;
        this.platformCoverageScore = platformCoverageScore;
        this.propertyCoverageScores = propertyCoverageScores;
        this.deploymentModelId = deploymentModelId;
    }

    public PlatformComparisonModel() {}

    public double getPlatformCoverageScore() {
        return platformCoverageScore;
    }

    public void setPlatformCoverageScore(double platformCoverageScore) {
        this.platformCoverageScore = platformCoverageScore;
    }

    public Map<String, Double> getPropertyCoverageScores() {
        return propertyCoverageScores;
    }

    public void setPropertyCoverageScores(Map<String, Double> propertyCoverageScores) {
        this.propertyCoverageScores = propertyCoverageScores;
    }

    public String getTargetPlatform() {
        return targetPlatform;
    }

    public void setTargetPlatform(String targetPlatform) {
        this.targetPlatform = targetPlatform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<Map<String, String>>> getEventSourceCoverage() {
        return eventSourceCoverage;
    }

    public void setEventSourceCoverage(Map<String, List<Map<String, String>>> eventSourceCoverage) {
        this.eventSourceCoverage = eventSourceCoverage;
    }

    public String getDeploymentModelId() {
        return deploymentModelId;
    }

    public void setDeploymentModelId(String deploymentModelId) {
        this.deploymentModelId = deploymentModelId;
    }
}
