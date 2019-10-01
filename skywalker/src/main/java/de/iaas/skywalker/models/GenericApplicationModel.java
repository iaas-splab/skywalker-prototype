package de.iaas.skywalker.models;

import java.util.List;
import java.util.Map;

public class GenericApplicationModel {

    private Map<String, List<String>> eventSources;
    private Map<String, List<String>> functions;
    private Map<String, List<String>> invokedServices;

    public GenericApplicationModel(Map<String, Map<String, List<String>>> appProperties) {
        this.eventSources = appProperties.get("EventSources");
        this.functions = appProperties.get("Function");
        this.invokedServices = appProperties.get("InvokedSources");
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
}
