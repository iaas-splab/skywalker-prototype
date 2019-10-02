package de.iaas.skywalker.controller;

import de.iaas.skywalker.models.GenericApplicationModel;
import de.iaas.skywalker.repository.GenericApplicationModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/models")
public class GenericApplicationModelController {
    private GenericApplicationModelRepository repository;

    public GenericApplicationModelController(GenericApplicationModelRepository genericApplicationModelRepository) {
        this.repository = genericApplicationModelRepository;
    }

    @GetMapping(path = "/")
    public Collection<GenericApplicationModel> get() { return this.repository.findAll(); }

    @PostMapping(path = "/")
    public ResponseEntity<Object> post(@RequestBody GenericApplicationModel genericApplicationModel) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/")
    public int put() { return 0; }

    @DeleteMapping(path = "/")
    public ResponseEntity<Object> delete() {
        this.repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
