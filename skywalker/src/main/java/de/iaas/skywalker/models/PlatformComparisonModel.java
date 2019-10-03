package de.iaas.skywalker.models;

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

    public PlatformComparisonModel(String id, String targetPlatform, Map<String, List<Map<String, String>>> eventSourceCoverage) {
        this.id = id;
        this.targetPlatform = targetPlatform;
        this.eventSourceCoverage = eventSourceCoverage;
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
}
