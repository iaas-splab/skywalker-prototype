package de.iaas.skywalker.controller;

import de.iaas.skywalker.evaluation.EvaluationHelper;
import de.iaas.skywalker.mapper.ModelMappingUtils;
import de.iaas.skywalker.models.GenericApplicationModel;
import de.iaas.skywalker.models.PlatformComparisonModel;
import de.iaas.skywalker.models.ServiceMapping;
import de.iaas.skywalker.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.repository.ServiceMappingRepository;
import de.iaas.skywalker.repository.ServicePropertyMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/models")
public class GenericApplicationModelController {
    private GenericApplicationModelRepository genericApplicationModelRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;
    private ServiceMappingRepository serviceMappingRepository;

    public GenericApplicationModelController(
            GenericApplicationModelRepository genericApplicationModelRepository,
            ServicePropertyMappingRepository servicePropertyMappingRepository,
            ServiceMappingRepository serviceMappingRepository
    ) {
        this.genericApplicationModelRepository = genericApplicationModelRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
        this.serviceMappingRepository = serviceMappingRepository;
    }

    @GetMapping(path = "/")
    public Collection<GenericApplicationModel> get() { return this.genericApplicationModelRepository.findAll(); }

    @PostMapping(path = "/")
    public PlatformComparisonModel getCoverageWithPlatformCandidate(@RequestBody GenericApplicationModel gam) {
        ModelMappingUtils utils = new ModelMappingUtils();

        List<ServiceMapping> azureServices = this.serviceMappingRepository.findByProvider("azure");
        Map<String, List<String>> azureEventSources = new HashMap<String, List<String>>() {{
            for(ServiceMapping sm : azureServices) {
                put(sm.getGenericResourceId(), sm.getServiceProperties());
            }
        }};
        azureEventSources = utils.generifyEventSourceProperties(azureEventSources, this.servicePropertyMappingRepository);

        EvaluationHelper eHelper = new EvaluationHelper(gam.getEventSources());
        double coverage = eHelper.evaluatePlatformCandidateCoverageScore(azureEventSources);

        Map<String, List<Map<String, String>>> compareModel = eHelper.getPlatformCandidateEventCoverageModel(azureEventSources);
        return new PlatformComparisonModel(gam.getId(), compareModel);
    }

    @PutMapping(path = "/")
    public int put() { return 0; }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> delete() {
        this.genericApplicationModelRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
