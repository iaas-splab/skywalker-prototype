package de.iaas.skywalker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class EventSourceMapping {
    @Id
    private String id;
    private String genericResourceId;
    private String provider;
    private String providerResourceId;
    private List<String> serviceProperties;

    public EventSourceMapping(String genericResourceId, String provider, String providerResourceId, List<String> serviceProperties) {
        this.genericResourceId = genericResourceId;
        this.provider = provider;
        this.providerResourceId = providerResourceId;
        this.serviceProperties = serviceProperties;
        this.id = provider + "_" + genericResourceId + "_" + providerResourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenericResourceId() {
        return genericResourceId;
    }

    public void setGenericResourceId(String genericResourceId) {
        this.genericResourceId = genericResourceId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderResourceId() {
        return providerResourceId;
    }

    public void setProviderResourceId(String providerResourceId) {
        this.providerResourceId = providerResourceId;
    }

    public List<String> getServiceProperties() {
        return serviceProperties;
    }

    public void setServiceProperties(List<String> serviceProperties) {
        this.serviceProperties = serviceProperties;
    }
}