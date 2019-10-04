package de.iaas.skywalker.DeploymentModels.repository;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface DeploymentModelRepository extends JpaRepository<DeploymentModel, Long> {

    List<DeploymentModel> findByName(String name);

    @Transactional
    Long deleteByName(String name);
}
