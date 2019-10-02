package de.iaas.skywalker.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class MappingConfiguration {
    @Id
    private String id;

    private @NonNull
    String deploymentModel;

    private @NonNull
    String mappingModule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeploymentModel() {
        return deploymentModel;
    }

    public void setDeploymentModel(String deploymentModel) {
        this.deploymentModel = deploymentModel;
    }

    public String getMappingModule() {
        return mappingModule;
    }

    public void setMappingModule(String mappingModule) {
        this.mappingModule = mappingModule;
    }
}
