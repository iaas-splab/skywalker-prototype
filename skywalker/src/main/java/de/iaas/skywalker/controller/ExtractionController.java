package de.iaas.skywalker.controller;

import de.iaas.skywalker.models.PlatformDeployment;
import de.iaas.skywalker.models.TemplateFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/extract")
public class ExtractionController {

    public ExtractionController() {}

    @GetMapping(path = "/")
    public int getExtraction() { return 0; }

    @PostMapping(path = "/")
    public ResponseEntity<Object> extractFromPlatform(@RequestBody PlatformDeployment platformDeployment) {
        System.out.println("ARN: " + platformDeployment.getArn() + "\n" + "Provider: " + platformDeployment.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping(path = "/template")
    public ResponseEntity<Object> putAvailableTemplate(@RequestBody TemplateFormat templateFormat) {
        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
        try {
            FileWriter fw = new FileWriter(currentPath + "\\test\\template." + templateFormat.fileFormat);
            fw.write(templateFormat.templateFileBody);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public int putExtraction() { return 0; }

    @DeleteMapping(path = "/")
    public int deleteExtraction() { return 0; }
}
