package de.iaas.skywalker.DeploymentPackages.controller;

import de.iaas.skywalker.CodeAnalysis.lambda.javalang.CodeDiscoverer;
import de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils.DiscoveryHelper;
import de.iaas.skywalker.DeploymentModels.model.AppExtractionData;
import de.iaas.skywalker.DeploymentPackages.model.DeploymentPackage;
import de.iaas.skywalker.DeploymentPackages.repository.DeploymentPackageRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/packages")
public class DeploymentPackageController {
    private DeploymentPackageRepository deploymentPackageRepository;
    Logger logger = LoggerFactory.getLogger(DeploymentPackageController.class);

    public DeploymentPackageController(DeploymentPackageRepository deploymentPackageRepository) {
        this.deploymentPackageRepository = deploymentPackageRepository;
    }

    @GetMapping(path = "/")
    public Collection<DeploymentPackage> getAll() { return this.deploymentPackageRepository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> crawlFromPlatform(@RequestBody AppExtractionData appExtractionData) {
        logger.debug("Extracting application from platform");
        logger.debug("(ARN: " + appExtractionData.getArn() + " , Provider: " + appExtractionData.getProvider() + ")");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/functions")
    public ResponseEntity<Object> analyzeFunctions(@RequestBody DeploymentPackage deploymentPackage) {
        if (deploymentPackage.getAnalyzed()) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        Map<String, String> analyzedFunctions = new HashMap<>();
        deploymentPackage.getFunctions().forEach((fnName, fnBody) -> {
            CodeDiscoverer cd = new CodeDiscoverer(fnBody, new ArrayList<String>(){{addAll(deploymentPackage.getFunctions().keySet());}});
            cd.discoverImports();
            cd.discoverClassMethods();
            DiscoveryHelper discoveryHelper = new DiscoveryHelper();
            analyzedFunctions.put(fnName, discoveryHelper.annotateHandlerBody(fnBody, cd.getCriticalTerms()));
        });

        deploymentPackage.setAnalyzed(true);
        deploymentPackage.setFunctions(analyzedFunctions);
        this.deploymentPackageRepository.save(deploymentPackage);

        return ResponseEntity.status(HttpStatus.OK).build();
        }

    @PutMapping(path = "/")
    public ResponseEntity<Object> putDeploymentModel(@RequestBody DeploymentModel deploymentModel) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.deploymentPackageRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
