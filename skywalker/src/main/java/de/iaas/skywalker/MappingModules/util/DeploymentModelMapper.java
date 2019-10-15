package de.iaas.skywalker.MappingModules.util;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DeploymentModelMapper {

    private Map<String, Object> deploymentModel;
    private String module_path;

    private static final String SELECT = "select";
    private static final String ROOT = "root";
    private static final String PATH = "path";
    private static final String VALUE = "value";
    private static final String WHERE = "where";

    public DeploymentModelMapper(DeploymentModel deploymentModel, String module_path) throws IOException {
        this.deploymentModel = this.loadHashMap(deploymentModel);
        this.module_path = module_path;
    }

    public DeploymentModelMapper() {}

    public Map<String, Object> loadHashMap(DeploymentModel deploymentModel) throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new ByteArrayInputStream(deploymentModel.getBody().getBytes());
        Map<String, Object> templateMap = yaml.load(inputStream);
        inputStream.close();
        return templateMap;
    }

    public Map<String, Object> extractApplicationProperties(String appProperty) {
        // Read mapping template from config file and limit the scope to the passed appProperty, e.g., 'eventSources'
        final Map<String, Object> MAPPING_MODULE = (Map<String, Object>) getMappingTemplate().get(appProperty);

        // Get SELECT config for mapping the resources
        final Map<String, Object> MAPPING_CONFIG = (Map<String, Object>) MAPPING_MODULE.get(SELECT);
        final List<String> MAPPING_CONFIG_ROOT = (MAPPING_CONFIG.get(ROOT) != null) ? (List<String>) MAPPING_CONFIG.get(ROOT) : new ArrayList<>();
        final List<String> MAPPING_CONFIG_PATH = (MAPPING_CONFIG.get(PATH) != null) ? (List<String>) MAPPING_CONFIG.get(PATH) : new ArrayList<>();

        // get WHERE config
        final Map<String, Object> STATEMENT_CONFIG = (MAPPING_MODULE.get(WHERE) != null) ? (Map<String, Object>) MAPPING_MODULE.get(WHERE) : new HashMap<>();
        final List<String> STATEMENT_PATH = (STATEMENT_CONFIG.get(PATH) != null) ? (List<String>) STATEMENT_CONFIG.get(PATH) : new ArrayList<>();
        final String STATEMENT_VALUE = (STATEMENT_CONFIG.get(VALUE) != null) ? (String) STATEMENT_CONFIG.get(VALUE) : "";

        // first get all resources from the SELECT ROOT
        Map<String, Object> root = this.deploymentModel;
        for (String node : MAPPING_CONFIG_ROOT) {
            try { root = (Map<String, Object>) root.get(node); }
            catch (ClassCastException e) {
                if (root.get(node) instanceof ArrayList) { root = this.handleArrayListCastExceptions(root, node);}
            }
            finally { if (root==null) return new HashMap<>(); }
        }

        // check WHERE statement inside of the ROOT
        Map<String, Object> results = new HashMap<>();
        root.forEach( (key,value) -> {
            String template_value = "";
            Map<String, Object> rootCopy = new HashMap<>();
            try { rootCopy = (Map<String, Object>) value; } // inhalt der potenziellen Lambda function #resourceInhalt
            catch (ClassCastException e) {
                try {
                    List<Map<String, Object>> rootCopyList = (List<Map<String, Object>>) value;
                    for (Map<String, Object> map : rootCopyList) {
                        results.put(map.keySet().iterator().next(), map.entrySet().iterator().next());
                    }
                } catch (ClassCastException c) {
                    try {
                        List<String> rootCopyList = (List<String>) value;
                        for (String s : rootCopyList) { results.put(s, s); }
                    }
                    catch (ClassCastException cs) { results.put((String) value, value); }
                }
            } catch (NullPointerException e) { return; }

            for (String node : STATEMENT_PATH) {
                try { rootCopy = (Map<String, Object>) rootCopy.get(node); }
                catch (ClassCastException e) { template_value = (String) rootCopy.get(node); }
            }
            if (template_value.equals(STATEMENT_VALUE)) {
                if (MAPPING_CONFIG_PATH.isEmpty()) { results.put(key.toString(), value); } // falls nicht explizit anders definiert, adde #resourceInhalt
                else {
                    Map<String, Object> thisRootTree = (Map<String, Object>) value;
                    for (String node : MAPPING_CONFIG_PATH) {
                        try { thisRootTree = (Map<String, Object>) thisRootTree.get(node); }
                        catch (ClassCastException e) {
                            if (thisRootTree.get(node) instanceof ArrayList)
                                thisRootTree = this.handleArrayListCastExceptions(thisRootTree, node);
                        }
                    }
                    results.putAll(thisRootTree);
                }
            }
        });
        return results;
    }

    private Map<String, Object> getMappingTemplate() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(this.module_path);
        return yaml.load(inputStream);
    }

    private Map<String, Object> handleArrayListCastExceptions(Map<String, Object> root, String node) {
        Map<String, Object> tempTree = new HashMap<>();
        try {
            for(Map<String, Object> property : (List<Map<String, Object>>) root.get(node)) {
                tempTree.putAll(property);
            }
        } catch (ClassCastException cMap) {
            try {
                for(String property : (List<String>) root.get(node)) { tempTree.put(property, property); }
            } catch (ClassCastException cList) {
                cList.printStackTrace();
            }
        }
        return tempTree;
    }
}

