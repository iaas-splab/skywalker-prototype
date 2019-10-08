package de.iaas.skywalker.DeploymentPackages.controller;

import de.iaas.skywalker.DeploymentModels.model.AppExtractionData;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.DeploymentPackages.model.DeploymentPackage;
import de.iaas.skywalker.DeploymentPackages.repository.DeploymentPackageRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Collection;

@RestController
@RequestMapping("/packages")
public class DeploymentPackageController {
    private DeploymentPackageRepository repository;

    public DeploymentPackageController(DeploymentPackageRepository deploymentPackageRepository) {
        this.repository = deploymentPackageRepository;
    }

    @GetMapping(path = "/")
    public Collection<DeploymentPackage> getAll() { return this.repository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> crawlFromPlatform(@RequestBody AppExtractionData appExtractionData) {
        System.out.println("ARN: " + appExtractionData.getArn() + "\n" + "Provider: " + appExtractionData.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<Object> putDeploymentModel(@RequestBody DeploymentModel deploymentModel) {
//        this.repository.deleteByName(deploymentModel.getName());
//        if(this.repository.findByName(deploymentModel.getName()) != null) this.repository.save(deploymentModel);
//        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
//        try {
//            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + deploymentModel.getName());
//            fw.write(deploymentModel.getBody());
//            fw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {}
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
