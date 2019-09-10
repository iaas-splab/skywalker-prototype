package de.iaas.skywalker.controller;

import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.repository.MappingModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Collection;

@RestController
@RequestMapping("/mapmodules")
public class MappingModuleController {
    private MappingModuleRepository repository;

    public MappingModuleController(MappingModuleRepository repository) {
        this.repository = repository;
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
            //Map<String, Object> yamlInHashMap = this.parseYAMLInHashMap();
            //Map<String, Map<String, Object>> generic_SAM_Template = this.analyzeTemplate(yamlInHashMap, "mapping.configurations/rule_serverless_v2.yaml");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
