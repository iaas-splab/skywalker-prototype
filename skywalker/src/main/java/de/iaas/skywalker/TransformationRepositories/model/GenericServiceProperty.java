package de.iaas.skywalker.TransformationRepositories.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class GenericServiceProperty {
    @Id
    private String genericResourceId;
    private Map<String, List<String>> genericServicePropertyMap;

    public GenericServiceProperty(String genericResourceId, Map<String, List<String>> genericServicePropertyMap) {
        this.genericResourceId = genericResourceId;
        this.genericServicePropertyMap = genericServicePropertyMap;
    }

    public String getGenericResourceId() {
        return genericResourceId;
    }

    public void setGenericResourceId(String genericResourceId) {
        this.genericResourceId = genericResourceId;
    }

    public Map<String, List<String>> getGenericServicePropertyMap() {
        return genericServicePropertyMap;
    }

    public void setGenericServicePropertyMap(Map<String, List<String>> genericServicePropertyMap) {
        this.genericServicePropertyMap = genericServicePropertyMap;
    }
}
