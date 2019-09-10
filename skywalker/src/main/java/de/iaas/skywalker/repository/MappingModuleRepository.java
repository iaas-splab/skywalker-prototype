package de.iaas.skywalker.repository;

import de.iaas.skywalker.models.MappingModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface MappingModuleRepository extends JpaRepository<MappingModule, Long> {

    List<MappingModule> findByName(String name);

    @Transactional
    Long deleteByName(String name);
}