package de.iaas.skywalker.ApplicationModels.controller;

import de.iaas.skywalker.ApplicationModels.model.CoverageEvaluationBundle;
import de.iaas.skywalker.ApplicationModels.model.GenericApplicationModel;
import de.iaas.skywalker.ApplicationModels.model.PlatformComparisonModel;
import de.iaas.skywalker.ApplicationModels.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.ApplicationModels.util.EvaluationHelper;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.MappingModules.model.ApplicationProperties;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.MappingModules.model.MappingConfiguration;
import de.iaas.skywalker.MappingModules.model.MappingModule;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
import de.iaas.skywalker.MappingModules.util.DeploymentModelDiscoverer;
import de.iaas.skywalker.MappingModules.util.ModelMappingUtils;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class GenericApplicationModelController {
    private MappingModuleRepository mappingModuleRepository;
    private DeploymentModelRepository deploymentModelRepository;
    private GenericApplicationModelRepository genericApplicationModelRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;
    private ServiceMappingRepository serviceMappingRepository;

    public GenericApplicationModelController(
            GenericApplicationModelRepository genericApplicationModelRepository,
            ServicePropertyMappingRepository servicePropertyMappingRepository,
            ServiceMappingRepository serviceMappingRepository,
            MappingModuleRepository mappingModuleRepository,
            DeploymentModelRepository deploymentModelRepository
    ) {
        this.genericApplicationModelRepository = genericApplicationModelRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
        this.serviceMappingRepository = serviceMappingRepository;
        this.mappingModuleRepository = mappingModuleRepository;
        this.deploymentModelRepository =  deploymentModelRepository;
    }

    @GetMapping(path = "/")
    public Collection<GenericApplicationModel> get() { return this.genericApplicationModelRepository.findAll(); }

    @PostMapping(path = "/")
    public PlatformComparisonModel evaluatePortability(@RequestBody CoverageEvaluationBundle bundle) {
        ModelMappingUtils utils = new ModelMappingUtils();

        List<EventSourceMapping> candidatePlatformServices = this.serviceMappingRepository.findByProvider(bundle.getTargetPlatformId());
        Map<String, List<String>> candidatePlatformEventSources = new HashMap<String, List<String>>() {{
            for(EventSourceMapping sm : candidatePlatformServices) {
                put(sm.getGenericResourceId(), sm.getServiceProperties());
            }
        }};
        candidatePlatformEventSources = utils.generifyEventSourceProperties(candidatePlatformEventSources, this.servicePropertyMappingRepository);

        EvaluationHelper evaluationHelper = new EvaluationHelper(bundle.getGam().getEventSources());

//        Map<String, List<String>> modelTranslationObject = evaluationHelper.getTranslatedTargetModel(
//                evaluationHelper.getPlatformCandidateEventCoverageModel(candidatePlatformEventSources),
//                this.serviceMappingRepository,
//                bundle.getTargetPlatformId()
//        );

        return new PlatformComparisonModel(
                bundle.getGam().getId(),
                bundle.getTargetPlatformId(),
                evaluationHelper.getPlatformCandidateEventCoverageModel(candidatePlatformEventSources),
                evaluationHelper.evaluatePlatformCandidateCoverageScore(candidatePlatformEventSources),
                evaluationHelper.evaluatePropertyCoverageScores(candidatePlatformEventSources)
        );
    }

    @PutMapping(path = "/")
    public ResponseEntity<Object> generateApplicationModel(@RequestBody MappingConfiguration mappingConfiguration) {
        // get deployment model and mapping model from repository
        List<DeploymentModel> deploymentModels = this.deploymentModelRepository.findByName(mappingConfiguration.getDeploymentModel());
        DeploymentModel deploymentModel = ((!(deploymentModels.size() > 1)) ? deploymentModels.get(0) : new DeploymentModel());
        List<MappingModule> mappingModules =  this.mappingModuleRepository.findByName(mappingConfiguration.getMappingModule());
        MappingModule mappingModule = ((!(mappingModules.size() > 1)) ? mappingModules.get(0) : new MappingModule());


        DeploymentModelDiscoverer discoverer = new DeploymentModelDiscoverer(deploymentModel, "mapping.configurations/" + mappingModule.getName());
        ApplicationProperties appProps = new ApplicationProperties(
                discoverer.extractApplicationProperties("EventSources"),
                discoverer.extractApplicationProperties("Function"),
                discoverer.extractApplicationProperties("InvokedServices")
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
        this.genericApplicationModelRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
