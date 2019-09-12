package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.ModelMapper;
import de.iaas.skywalker.mapper.PlatformSpecificModel;
import de.iaas.skywalker.models.MappingConfiguration;
import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.Template;
import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/mapping")
public class MappingController {
    private MappingModuleRepository mappingModuleRepository;
    private TemplateRepository templateRepository;

    private final List<String> genericPropertyTypes = new ArrayList<String>() {{
        add("EventSources");
        add("Function");
        add("InvokedServices");
    }};

    public MappingController(MappingModuleRepository mappingModuleRepository, TemplateRepository templateRepository) {
        this.mappingModuleRepository = mappingModuleRepository;
        this.templateRepository = templateRepository;
    }

    @GetMapping(path = "/")
    public Collection<MappingModule> getMappingModules() { return this.mappingModuleRepository.findAll(); }

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> putMappingModuleFile(@RequestBody MappingModule module) {
        this.mappingModuleRepository.deleteByName(module.getName());
        if(this.mappingModuleRepository.findByName(module.getName()) != null) this.mappingModuleRepository.save(module);
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
        List<MappingModule> findingsByMappingName =  this.mappingModuleRepository.findByName(mappingConfiguration.getMappingModule());
        MappingModule mappingModule = ((!(findingsByMappingName.size() > 1)) ? findingsByMappingName.get(0) : new MappingModule());

        ModelMapper mapper = new ModelMapper(template, "mapping.configurations/" + mappingModule.getName());
        Map<String, Map<String, Object>> mappedTemplate = mapper.translateIntoGenericModel(genericPropertyTypes);

        PlatformSpecificModel psm = new PlatformSpecificModel(mappedTemplate);
        psm.mapEntryToStringList("EventSources");
        psm.mapEntryToStringList("Function");

        psm.makePAM();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
