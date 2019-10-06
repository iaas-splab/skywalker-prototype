package de.iaas.skywalker.TransformationRepositories.controller;

import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tables")
public class MappingTableController {

    private ServiceMappingRepository serviceMappingRepository;
    private ServicePropertyMappingRepository servicePropertyMappingRepository;


    public MappingTableController(
            ServiceMappingRepository serviceMappingRepository,
            ServicePropertyMappingRepository servicePropertyMappingRepository) {
        this.serviceMappingRepository = serviceMappingRepository;
        this.servicePropertyMappingRepository = servicePropertyMappingRepository;
    }

    @GetMapping(path = "/services")
    public Collection<EventSourceMapping> getEventSourceMappingTable() { return this.serviceMappingRepository.findAll(); }

    @GetMapping(path = "/properties")
    public Collection<GenericServiceProperty> getServicePropertyMappingTable() { return this.servicePropertyMappingRepository.findAll(); }

    @PutMapping(path = "/services")
    public ResponseEntity<Object> putServiceMapping(@RequestBody LinkedHashMap map) {
        this.serviceMappingRepository.save(
                new EventSourceMapping(
                        (String) map.get("genericResourceId"),
                        (String) map.get("provider"),
                        (String) map.get("providerResourceId"),
                        (List<String>) map.get("serviceProperties")
                )
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/properties")
    public ResponseEntity<Object> putPropertyMapping(@RequestBody LinkedHashMap map) {
        this.servicePropertyMappingRepository.save(
                new GenericServiceProperty(
                        (String) map.get("genericResourceId"),
                        (Map<String, List<String>>) map.get("genericServicePropertyMap")
                )
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/services")
    public ResponseEntity<Object> deleteAllServiceMappings() {
        this.serviceMappingRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/properties")
    public ResponseEntity<Object> deleteAllPropertyMappings() {
        this.servicePropertyMappingRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
