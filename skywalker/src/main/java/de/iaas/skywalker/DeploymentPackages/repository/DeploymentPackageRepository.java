package de.iaas.skywalker.DeploymentPackages.repository;

import de.iaas.skywalker.DeploymentPackages.model.DeploymentPackage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeploymentPackageRepository extends MongoRepository<DeploymentPackage, String> {

}
