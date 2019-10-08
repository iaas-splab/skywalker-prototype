package de.iaas.skywalker.DeploymentPackages.model;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class DeploymentPackage {
    @Id
    private String id;
    private boolean analyzed;
    private Map<String, String> functions;
    private String deploymentModel;

    public boolean getAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, String> functions) {
        this.functions = functions;
    }

    public String getDeploymentModel() {
        return deploymentModel;
    }

    public void setDeploymentModel(String deploymentModel) {
        this.deploymentModel = deploymentModel;
    }
}
