package de.iaas.skywalker.ApplicationModels.model;

import de.iaas.skywalker.MappingModules.model.ServiceMetaData;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document
@NoArgsConstructor
public class GenericApplicationModel {

    @Id
    private String id;
    private Map<String, List<ServiceMetaData>> eventSources;
    private Map<String, List<String>> functions;
    private Map<String, List<String>> invokedServices;

    public GenericApplicationModel(String appName, Map<String, Map<String, List<String>>> appProperties) {
        this.eventSources = this.setEventSources();
        this.functions = appProperties.get("Function");
        this.invokedServices = appProperties.get("InvokedSources");
        this.id = appName;
    }

    public Map<String, List<ServiceMetaData>> getEventSources() {
        return eventSources;
    }

    public void setEventSources(Map<String, List<String>> eventSources) {
        eventSources.forEach(
                (key, value) -> this.eventSources.put(key, new ArrayList<ServiceMetaData>() {{
                    value.stream().forEach(
                            x -> add(new ServiceMetaData(x, new String()))
                    );
                }})
        );
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
}
