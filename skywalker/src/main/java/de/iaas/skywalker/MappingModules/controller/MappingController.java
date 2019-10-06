package de.iaas.skywalker.MappingModules.controller;

import de.iaas.skywalker.MappingModules.model.MappingModule;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
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

    public MappingController(MappingModuleRepository mappingModuleRepository) {
        this.mappingModuleRepository = mappingModuleRepository;
    }

    @GetMapping(path = "/")
    public Collection<MappingModule> getMappingModules() { return this.mappingModuleRepository.findAll(); }

    @PutMapping(path = "/")
    public ResponseEntity<Object> uploadMappingModule(@RequestBody MappingModule module) { // Todo: Sometimes still adds duplicate mapping modules per request
        this.mappingModuleRepository.deleteByName(module.getName()); // TOdo: crashes when already existing mapping module is tried to be added again
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

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.mappingModuleRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
