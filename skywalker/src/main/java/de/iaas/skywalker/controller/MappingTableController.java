package de.iaas.skywalker.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.util.JSON;
import de.iaas.skywalker.models.EventSourceMapping;
import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.repository.ServiceMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tables")
public class MappingTableController {

    private ServiceMappingRepository serviceMappingRepository;


    public MappingTableController(ServiceMappingRepository serviceMappingRepository) {
        this.serviceMappingRepository = serviceMappingRepository;
    }

    @GetMapping(path = "/services")
    public Collection<EventSourceMapping> getMappingModules() { return this.serviceMappingRepository.findAll(); }

    @PostMapping(path = "/services")
    public ResponseEntity<Object> putMappingModuleFile(@RequestBody LinkedHashMap map) {
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

    @DeleteMapping(path = "/services")
    public ResponseEntity<Object> deleteAll() {
        this.serviceMappingRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
