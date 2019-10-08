package de.iaas.skywalker.DeploymentPackages.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class DeploymentPackage {
    @Id
    private String id;
    private List<String> functions;
    private String deploymentModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public String getDeploymentModel() {
        return deploymentModel;
    }

    public void setDeploymentModel(String deploymentModel) {
        this.deploymentModel = deploymentModel;
    }
}
