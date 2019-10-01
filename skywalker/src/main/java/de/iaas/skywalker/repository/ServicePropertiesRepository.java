package de.iaas.skywalker.repository;

import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.ServiceProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface ServicePropertiesRepository extends JpaRepository<ServiceProperty, Long> {

    List<ServiceProperty> findByProperty(String property);
    List<ServiceProperty> findByPlatform(String platform);
    List<ServiceProperty> findByGenericResourceName(String genericResourceName);
    List<ServiceProperty> findByGenericPropertyName(String genericPropertyName);

//    @Transactional
//    Long deleteByName(String name);
}
