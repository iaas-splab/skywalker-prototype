package de.iaas.skywalker.repository;


import de.iaas.skywalker.models.ServiceMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceMappingRepository extends MongoRepository<ServiceMapping, Long> {

    List<ServiceMapping> findByProviderResourceId(String providerResourceId);
    List<ServiceMapping> findByGenericResourceId(String genericResourceId);
    List<ServiceMapping> findByProvider(String provider);
}
