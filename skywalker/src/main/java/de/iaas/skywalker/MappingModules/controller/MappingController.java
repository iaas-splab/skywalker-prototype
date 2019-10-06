package de.iaas.skywalker.MappingModules.controller;

import de.iaas.skywalker.ApplicationModels.model.GenericApplicationModel;
import de.iaas.skywalker.ApplicationModels.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.MappingModules.model.ApplicationProperties;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.MappingModules.model.MappingConfiguration;
import de.iaas.skywalker.MappingModules.model.MappingModule;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
import de.iaas.skywalker.MappingModules.util.DeploymentModelMapper;
import de.iaas.skywalker.MappingModules.util.ModelMappingUtils;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
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
    private DeploymentModelRepository deploymentModelRepository;
    private ServiceMappingRepository serviceMappingRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;
    private GenericApplicationModelRepository genericApplicationModelRepository;

    public MappingController(
            MappingModuleRepository mappingModuleRepository,
            DeploymentModelRepository deploymentModelRepository,
            ServiceMappingRepository serviceMappingRepository,
            ServicePropertyMappingRepository servicePropertyMappingRepository,
            GenericApplicationModelRepository genericApplicationModelRepository
            ) {
        this.mappingModuleRepository = mappingModuleRepository;
        this.deploymentModelRepository = deploymentModelRepository;
        this.serviceMappingRepository = serviceMappingRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
        this.genericApplicationModelRepository = genericApplicationModelRepository;
    }

    @GetMapping(path = "/")
    public Collection<MappingModule> getMappingModules() { return this.mappingModuleRepository.findAll(); }

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> putMappingModuleFile(@RequestBody MappingModule module) { // Todo: Sometimes still adds duplicate mapping modules per request
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
