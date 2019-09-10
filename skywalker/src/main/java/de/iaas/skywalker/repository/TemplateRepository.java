package de.iaas.skywalker.repository;

import de.iaas.skywalker.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findByName(String name);

    @Transactional
    Long deleteByName(String name);
}
