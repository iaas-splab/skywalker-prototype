package de.iaas.skywalker.repository;

import de.iaas.skywalker.models.GenericApplicationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericApplicationModelRepository extends MongoRepository<GenericApplicationModel, Long> {

}
