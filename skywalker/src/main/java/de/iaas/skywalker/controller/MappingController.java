package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.ModelMapper;
import de.iaas.skywalker.models.MappingConfiguration;
import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.Template;
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
    private TemplateRepository templateRepository;

    public MappingController(MappingModuleRepository repository, TemplateRepository templateRepository) {
        this.repository = repository;
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
        } finally {}
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/generate")
    public ResponseEntity<Object> generateGenericApplicationModelMapping(@RequestBody MappingConfiguration mappingConfiguration) {
        List<Template> findingsByTemplateName = this.templateRepository.findByName(mappingConfiguration.getTemplate());
        Template template = ((!(findingsByTemplateName.size() > 1)) ? findingsByTemplateName.get(0) : new Template());
        List<MappingModule> findingsByMappingName =  this.repository.findByName(mappingConfiguration.getMappingModule());
        MappingModule mappingModule = ((!(findingsByMappingName.size() > 1)) ? findingsByMappingName.get(0) : new MappingModule());

        Map<String, Object> templateYAML = parseYAMLInHashMap(template.getName());
        Map<String, Map<String, Object>> genericTemplate = analyzeTemplate(templateYAML,
                "mapping.configurations/" + mappingModule.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
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
