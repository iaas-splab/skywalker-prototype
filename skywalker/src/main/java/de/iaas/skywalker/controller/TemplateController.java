package de.iaas.skywalker.controller;

import de.iaas.skywalker.mapper.ModelMapper;
import de.iaas.skywalker.models.PlatformDeployment;
import de.iaas.skywalker.models.Template;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.InputStream;
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
    public Collection<Template> getTemplates() { return this.repository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> extractFromPlatform(@RequestBody PlatformDeployment platformDeployment) {
        System.out.println("ARN: " + platformDeployment.getArn() + "\n" + "Provider: " + platformDeployment.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping(path = "/upload")
    public ResponseEntity<Object> putTemplateFile(@RequestBody Template template) {
        this.repository.deleteByName(template.getName());
        if(this.repository.findByName(template.getName()) != null) this.repository.save(template);
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + template.getName());
            fw.write(template.getBody());
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
