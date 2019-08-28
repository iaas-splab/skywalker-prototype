package de.iaas.skywalker.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extract")
public class ExtractionController {

    public ExtractionController() {}

    @GetMapping(path = "/")
    public int getExtraction() { return 0; }

    @PostMapping(path = "/")
    public int postExtraction() { return 0; }

    @PutMapping(path = "/")
    public int putExtraction() { return 0; }

    @DeleteMapping(path = "/")
    public int deleteExtraction() { return 0; }
}
