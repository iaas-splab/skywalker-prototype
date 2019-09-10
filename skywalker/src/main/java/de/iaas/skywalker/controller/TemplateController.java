package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.ModelMapper;
import de.iaas.skywalker.models.PlatformDeployment;
import de.iaas.skywalker.models.Template;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/extract")
public class TemplateController {
    private TemplateRepository repository;

    private final List<String> genericPropertyTypes = new ArrayList<String>() {{
        add("EventSources");
        add("Function");
        add("InvokedServices");
    }};

    public TemplateController(TemplateRepository templateRepository) {
        this.repository = templateRepository;
    }

    @GetMapping(path = "/")
    public int getExtraction() { return 0; }

    @PostMapping(path = "/")
    public ResponseEntity<Object> extractFromPlatform(@RequestBody PlatformDeployment platformDeployment) {
        System.out.println("ARN: " + platformDeployment.getArn() + "\n" + "Provider: " + platformDeployment.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping(path = "/template")
    public ResponseEntity<Object> putTemplateFile(@RequestBody Template template) {
        this.repository.save(template);
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + template.getName());
            fw.write(template.getBody());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            //Map<String, Object> yamlInHashMap = this.parseYAMLInHashMap();
            //Map<String, Map<String, Object>> generic_SAM_Template = this.analyzeTemplate(yamlInHashMap, "mapping.configurations/rule_serverless_v2.yaml");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public int putExtraction() { return 0; }

    @DeleteMapping(path = "/")
    public int deleteExtraction() { return 0; }

    private Map<String, Object> parseYAMLInHashMap() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/serverless.yml");
        Map<String, Object> templateMap = yaml.load(inputStream);
        return templateMap;
    }

    public Map<String, Map<String, Object>> analyzeTemplate(Map<String, Object> template, String ruleFilePath) {
        ModelMapper mapper = new ModelMapper(template, ruleFilePath);
        Map<String, Map<String, Object>> results = new HashMap<>();
        for(String genericPropType : genericPropertyTypes) {
            Map<String, Object> collectionOfResults = mapper.modelTransformationWithMappingTemplate(genericPropType);
            results.put(genericPropType, collectionOfResults);
        }
        return results;
    }
}
