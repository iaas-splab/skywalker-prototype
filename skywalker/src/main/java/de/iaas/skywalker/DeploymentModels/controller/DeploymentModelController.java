package de.iaas.skywalker.DeploymentModels.controller;

import de.iaas.skywalker.DeploymentModels.model.AppExtractionData;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.Utils.ExecUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/templates")
public class DeploymentModelController {
    private DeploymentModelRepository repository;

    public DeploymentModelController(DeploymentModelRepository deploymentModelRepository) {
        this.repository = deploymentModelRepository;
    }

    @GetMapping(path = "/")
    public Collection<DeploymentModel> getAll() { return this.repository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> crawlFromPlatform(@RequestBody AppExtractionData appExtractionData) throws IOException {
        System.out.println("ARN: " + appExtractionData.getArn() + "\n" + "Provider: " + appExtractionData.getProvider());

        ExecUtils execUtils = new ExecUtils();
        execUtils.runScript("sh /src/main/crawler/aws/grab_lambdas.sh");
        execUtils.runScript("sh /src/main/crawler/aws/grab_functions.sh");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<Object> putDeploymentModel(@RequestBody DeploymentModel deploymentModel) {
        this.repository.deleteByName(deploymentModel.getName());
        if(this.repository.findByName(deploymentModel.getName()) != null) this.repository.save(deploymentModel);
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + deploymentModel.getName());
            fw.write(deploymentModel.getBody());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {}
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
