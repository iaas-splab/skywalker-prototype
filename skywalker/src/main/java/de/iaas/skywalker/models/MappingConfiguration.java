package de.iaas.skywalker.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class MappingConfiguration {
    @Id
    @GeneratedValue
    private Long id;

    private @NonNull
    String template;

    private @NonNull
    String mappingModule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMappingModule() {
        return mappingModule;
    }

    public void setMappingModule(String mappingModule) {
        this.mappingModule = mappingModule;
    }
}
