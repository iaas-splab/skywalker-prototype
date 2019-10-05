package de.iaas.skywalker.MappingModules.model;

public class ServiceMetaData {

    private String providerSpecificId;
    private String genericId;

    public ServiceMetaData(String genericId, String providerSpecificId) {
        this.genericId = genericId;
        this.providerSpecificId = providerSpecificId;
    }

    public String getProviderSpecificId() {
        return providerSpecificId;
    }

    public void setProviderSpecificId(String providerSpecificId) {
        this.providerSpecificId = providerSpecificId;
    }

    public String getGenericId() {
        return genericId;
    }

    public void setGenericId(String genericId) {
        this.genericId = genericId;
    }
}
