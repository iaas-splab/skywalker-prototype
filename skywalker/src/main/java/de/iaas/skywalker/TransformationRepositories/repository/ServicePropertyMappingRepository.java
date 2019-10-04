package de.iaas.skywalker.TransformationRepositories.repository;

import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface ServicePropertyMappingRepository extends MongoRepository<GenericServiceProperty, Long> {

    List<GenericServiceProperty> findByGenericResourceId(String genericResourceId);
}
