package de.iaas.skywalker.controller;

import de.iaas.skywalker.models.PlatformDeployment;
import de.iaas.skywalker.models.DeploymentModel;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    private TemplateRepository repository;

    public TemplateController(TemplateRepository templateRepository) {
        this.repository = templateRepository;
    }

    @GetMapping(path = "/")
    public Collection<DeploymentModel> getTemplates() { return this.repository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> extractFromPlatform(@RequestBody PlatformDeployment platformDeployment) {
        System.out.println("ARN: " + platformDeployment.getArn() + "\n" + "Provider: " + platformDeployment.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping(path = "/upload")
    public ResponseEntity<Object> putTemplateFile(@RequestBody DeploymentModel deploymentModel) {
        this.repository.deleteByName(deploymentModel.getName());
        if(this.repository.findByName(deploymentModel.getName()) != null) this.repository.save(deploymentModel);
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + deploymentModel.getName());
            fw.write(deploymentModel.getBody());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {}
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public int putExtraction() { return 0; }

    @DeleteMapping(path = "/")
    public int deleteExtraction() { return 0; }
}
