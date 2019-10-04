package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.DeploymentModelMapper;
import de.iaas.skywalker.mapper.ModelMappingUtils;
import de.iaas.skywalker.models.*;
import de.iaas.skywalker.repository.*;
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
        // get deployment model and mapping model from repository
        List<DeploymentModel> deploymentModels = this.deploymentModelRepository.findByName(mappingConfiguration.getDeploymentModel());
        DeploymentModel deploymentModel = ((!(deploymentModels.size() > 1)) ? deploymentModels.get(0) : new DeploymentModel());
        List<MappingModule> mappingModules =  this.mappingModuleRepository.findByName(mappingConfiguration.getMappingModule());
        MappingModule mappingModule = ((!(mappingModules.size() > 1)) ? mappingModules.get(0) : new MappingModule());


        DeploymentModelMapper mapper = new DeploymentModelMapper(deploymentModel, "mapping.configurations/" + mappingModule.getName());
        ApplicationProperties appProps = new ApplicationProperties(
                mapper.extractApplicationProperties("EventSources"),
                mapper.extractApplicationProperties("Function"),
                mapper.extractApplicationProperties("InvokedServices")
        );


        ModelMappingUtils utils = new ModelMappingUtils();
        GenericApplicationModel GAM = new GenericApplicationModel(mappingConfiguration.getId(), utils.getAppAtPropertiesLevel(appProps));
        GAM.setEventSources(utils.generifyEventSourceNames(GAM.getEventSources().entrySet().iterator(), this.serviceMappingRepository));
        GAM.setEventSources(utils.generifyEventSourceProperties(GAM.getEventSources(), this.servicePropertyMappingRepository));
//        GAM.setFunctions(utils.generifyEventSourceNames(GAM.getFunctions().entrySet().iterator(), this.serviceMappingRepository));
//        GAM.setInvokedServices(utils.generifyEventSourceNames(GAM.getInvokedServices().entrySet().iterator(), this.serviceMappingRepository));
        this.genericApplicationModelRepository.save(GAM);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.mappingModuleRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
