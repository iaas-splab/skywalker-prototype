package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.ModelMapper;
import de.iaas.skywalker.models.GenericApplicationModel;
import de.iaas.skywalker.models.MappingConfiguration;
import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.Template;
import de.iaas.skywalker.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/mapping")
public class MappingController {
    private MappingModuleRepository repository;
    private GenericApplicationModelRepository gamRepository;
    private TemplateRepository templateRepository;

    public MappingController(MappingModuleRepository repository, GenericApplicationModelRepository gamRepository,
                             TemplateRepository templateRepository) {
        this.repository = repository;
        this.gamRepository = gamRepository;
        this.templateRepository = templateRepository;
    }

    @GetMapping(path = "/")
    public Collection<MappingModule> getMappingModules() { return this.repository.findAll(); }

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> putMappingModuleFile(@RequestBody MappingModule module) {
        this.repository.deleteByName(module.getName());
        if(this.repository.findByName(module.getName()) != null) this.repository.save(module);
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\mapping.configurations\\" + module.getName());
            fw.write(module.getBody());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
//            Map<String, Object> yamlInHashMap = this.parseYAMLInHashMap();
//            Map<String, Map<String, Object>> generic_SAM_Template = this.analyzeTemplate(yamlInHashMap, "mapping.configurations/rule_serverless_v2.yaml");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/generate")
    public ResponseEntity<Object> generateGenericApplicationModelMapping(@RequestBody MappingConfiguration mappingConfiguration) {
        Template template = (Template) this.templateRepository.findByName(mappingConfiguration.getTemplate());
        MappingModule mappingModule = (MappingModule) this.repository.findByName(mappingConfiguration.getMappingModule());

        Map<String, Object> templateYAML = parseYAMLInHashMap(mappingConfiguration.getTemplate());
        Map<String, Map<String, Object>> genericTemplate = analyzeTemplate(templateYAML,
                "mapping.configurations/" + mappingConfiguration.getMappingModule());
        GenericApplicationModel GAM = new GenericApplicationModel();
        GAM.setName(generateUniqueAppModelName(template, mappingModule));
        GAM.setGenericApplicationProperties(genericTemplate);
        this.gamRepository.deleteByName(GAM.getName());
        if(this.gamRepository.findByName(GAM.getName()) != null)
            this.gamRepository.save(GAM);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private String generateUniqueAppModelName(Template template, MappingModule mappingModule) {
        return mappingModule.getName().split(".")[0] + template.getId();
    }

    private final List<String> genericPropertyTypes = new ArrayList<String>() {{
        add("EventSources");
        add("Function");
        add("InvokedServices");
    }};

    private Map<String, Object> parseYAMLInHashMap(String templateName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/" + templateName);
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
