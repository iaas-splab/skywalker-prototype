package de.iaas.skywalker.utils;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;

import java.io.*;

public class TestUtils {

    public DeploymentModel load_deployment_model_from_file(String file_path) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file_path)));
        DeploymentModel model = new DeploymentModel();
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
        model.setName("aws_test_model");
        model.setBody(sb.toString());
        return model;
    }

    public String load_string_from_file(String file_path) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file_path)));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
        return sb.toString();
    }
}
