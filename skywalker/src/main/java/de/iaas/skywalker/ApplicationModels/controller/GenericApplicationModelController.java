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
import de.iaas.skywalker.MappingModules.util.DeploymentModelMapper;
import de.iaas.skywalker.MappingModules.util.ModelMappingUtils;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Translator.ServerlessFramework.Azure.AzureTemplateGenerator;
import de.iaas.skywalker.Translator.ServerlessFramework.TemplateGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping(path = "/evaluation")
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

        return new PlatformComparisonModel(
                bundle.getGam().getId(),
                bundle.getTargetPlatformId(),
                evaluationHelper.getPlatformCandidateEventCoverageModel(candidatePlatformEventSources),
                evaluationHelper.evaluateTargetPlatformCoverageScore(candidatePlatformEventSources),
                evaluationHelper.evaluteEventCoverageScores(candidatePlatformEventSources),
                bundle.getGam().getOriginalDeploymentModelId()
        );
    }

    @PostMapping(path = "/translation")
    public ResponseEntity<Object> translateToTargetPlatformModel(@RequestBody PlatformComparisonModel model) throws IOException {
        String sourceDeploymentModel = model.getDeploymentModelId();
        DeploymentModel deploymentModel = this.deploymentModelRepository.findByName(sourceDeploymentModel).get(0);

        if (model.getTargetPlatform().equals("azure")) {
            TemplateGenerator generator = new AzureTemplateGenerator(deploymentModel, this.serviceMappingRepository, this.servicePropertyMappingRepository);
            deploymentModel.setBody(((AzureTemplateGenerator) generator).translateSourceDeploymentModelToTargetProviderTemplate());
            deploymentModel.setName("azure" + "_" + deploymentModel.getName());
            this.deploymentModelRepository.save(deploymentModel);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<Object> generateApplicationModel(@RequestBody MappingConfiguration mappingConfiguration) throws IOException {
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
        GenericApplicationModel GAM = new GenericApplicationModel(mappingConfiguration.getId(), utils.getAppAtPropertiesLevel(appProps), deploymentModel.getName());
        GAM.setEventSources(utils.generifyEventSourceNames(GAM.getEventSources().entrySet().iterator(), this.serviceMappingRepository));
        GAM.setEventSources(utils.generifyEventSourceProperties(GAM.getEventSources(), this.servicePropertyMappingRepository));
        this.genericApplicationModelRepository.save(GAM);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.genericApplicationModelRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
