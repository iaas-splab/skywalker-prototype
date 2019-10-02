package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.DeploymentModelMapper;
import de.iaas.skywalker.evaluation.EvaluationHelper;
import de.iaas.skywalker.mapper.ModelMappingUtils;
import de.iaas.skywalker.models.*;
import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.ServiceMappingRepository;
import de.iaas.skywalker.repository.DeploymentModelRepository;
import de.iaas.skywalker.repository.ServicePropertyMappingRepository;
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

    public MappingController(
            MappingModuleRepository mappingModuleRepository,
            DeploymentModelRepository deploymentModelRepository,
            ServiceMappingRepository serviceMappingRepository,
            ServicePropertyMappingRepository servicePropertyMappingRepository
            ) {
        this.mappingModuleRepository = mappingModuleRepository;
        this.deploymentModelRepository = deploymentModelRepository;
        this.serviceMappingRepository = serviceMappingRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
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
        List<DeploymentModel> deploymentModels = this.deploymentModelRepository.findByName(mappingConfiguration.getDeploymentModel());
        DeploymentModel deploymentModel = ((!(deploymentModels.size() > 1)) ? deploymentModels.get(0) : new DeploymentModel());

        List<MappingModule> mappingModules =  this.mappingModuleRepository.findByName(mappingConfiguration.getMappingModule());
        MappingModule mappingModule = ((!(mappingModules.size() > 1)) ? mappingModules.get(0) : new MappingModule());

        DeploymentModelMapper mapper = new DeploymentModelMapper(deploymentModel, "mapping.configurations/" + mappingModule.getName());

        ApplicationProperties appProps = new ApplicationProperties();
        appProps.setEventSources(mapper.extractApplicationProperties("EventSources"));
        appProps.setInvokedServices(mapper.extractApplicationProperties("InvokedServices"));
        appProps.setFunctions(mapper.extractApplicationProperties("Function"));

        ModelMappingUtils utils = new ModelMappingUtils();

        GenericApplicationModel GAM = new GenericApplicationModel(utils.getAppAtPropertiesLevel(appProps));
        GAM.setEventSources(utils.makeGrid(GAM.getEventSources().entrySet().iterator(), this.serviceMappingRepository));
//        GAM.setFunctions(utils.makeGrid(GAM.getFunctions().entrySet().iterator(), this.serviceMappingRepository));
//        GAM.setInvokedServices(utils.makeGrid(GAM.getInvokedServices().entrySet().iterator(), this.serviceMappingRepository));

        List<ServiceMapping> azureServices = this.serviceMappingRepository.findByProvider("azure");
        Map<String, List<String>> azureEventSources = new HashMap<String, List<String>>() {{
            for(ServiceMapping sm : azureServices) {
                put(sm.getGenericResourceId(), sm.getServiceProperties());
            }
        }};
        azureEventSources = utils.genericPropertiesForGAM(azureEventSources, this.servicePropertyMappingRepository);

        List<GenericServiceProperty> serviceProperties = this.servicePropertyMappingRepository.findAll();

        GAM.setEventSources(utils.genericPropertiesForGAM(GAM.getEventSources(), this.servicePropertyMappingRepository));

        EvaluationHelper eHelper = new EvaluationHelper(GAM.getEventSources());
        double coverage = eHelper.compareWithSelectedPlatformCandidate(azureEventSources);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
