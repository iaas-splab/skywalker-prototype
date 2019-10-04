package de.iaas.skywalker.ApplicationModels.repository;

import de.iaas.skywalker.ApplicationModels.model.GenericApplicationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericApplicationModelRepository extends MongoRepository<GenericApplicationModel, Long> {

}
