package de.iaas.skywalker.ApplicationModels.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
@NoArgsConstructor
public class GenericApplicationModel {

    @Id
    private String id;
    private Map<String, List<String>> eventSources;
    private Map<String, List<String>> functions;
    private Map<String, List<String>> invokedServices;
    private String originalDeploymentModelId;

    public GenericApplicationModel(String appName, Map<String, Map<String, List<String>>> appProperties, String originalDeploymentModelId) {
        this.eventSources = appProperties.get("EventSources");
        this.functions = appProperties.get("Function");
        this.invokedServices = appProperties.get("InvokedSources");
        this.id = appName;
        this.originalDeploymentModelId = originalDeploymentModelId;
    }

    public Map<String, List<String>> getEventSources() {
        return eventSources;
    }

    public void setEventSources(Map<String, List<String>> eventSources) {
        this.eventSources = eventSources;
    }

    public Map<String, List<String>> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    public Map<String, List<String>> getInvokedServices() {
        return invokedServices;
    }

    public void setInvokedServices(Map<String, List<String>> invokedServices) {
        this.invokedServices = invokedServices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalDeploymentModelId() {
        return originalDeploymentModelId;
    }

    public void setOriginalDeploymentModelId(String originalDeploymentModelId) {
        this.originalDeploymentModelId = originalDeploymentModelId;
    }
}
