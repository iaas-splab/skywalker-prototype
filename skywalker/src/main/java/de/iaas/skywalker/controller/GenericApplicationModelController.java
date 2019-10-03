package de.iaas.skywalker.controller;

import de.iaas.skywalker.evaluation.EvaluationHelper;
import de.iaas.skywalker.mapper.ModelMappingUtils;
import de.iaas.skywalker.models.CoverageEvaluationBundle;
import de.iaas.skywalker.models.GenericApplicationModel;
import de.iaas.skywalker.models.PlatformComparisonModel;
import de.iaas.skywalker.models.EventSourceMapping;
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
    public PlatformComparisonModel getCoverageWithPlatformCandidate(@RequestBody CoverageEvaluationBundle bundle) {
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
                evaluationHelper.evaluatePlatformCandidateCoverageScore(candidatePlatformEventSources),
                evaluationHelper.evaluatePropertyCoverageScores(candidatePlatformEventSources)
        );
    }

    @PutMapping(path = "/")
    public int put() { return 0; }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.genericApplicationModelRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
