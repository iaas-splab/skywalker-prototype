package de.iaas.skywalker.TransformationRepositories.repository;


import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceMappingRepository extends MongoRepository<EventSourceMapping, Long> {

    List<EventSourceMapping> findByProviderResourceId(String providerResourceId);
    List<EventSourceMapping> findByGenericResourceId(String genericResourceId);
    List<EventSourceMapping> findByProvider(String provider);
}
