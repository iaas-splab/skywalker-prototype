package de.iaas.skywalker.controller;

import de.iaas.skywalker.models.Template;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extract")
public class ExtractionController {

    public ExtractionController() {}

    @GetMapping(path = "/")
    public int getExtraction() { return 0; }

    @PostMapping(path = "/")
    public ResponseEntity<Object> postExtraction(@RequestBody Template template) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public int putExtraction() { return 0; }

    @DeleteMapping(path = "/")
    public int deleteExtraction() { return 0; }
}
