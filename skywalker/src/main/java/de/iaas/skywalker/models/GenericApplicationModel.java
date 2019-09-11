package de.iaas.skywalker.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class GenericApplicationModel {
    @Id
    @GeneratedValue
    private Long id;

    private @NonNull
    String name;

    private @NonNull
    Map<String, Map<String, Object>> genericApplicationProperties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Map<String, Object>> getGenericApplicationProperties() {
        return genericApplicationProperties;
    }

    public void setGenericApplicationProperties(Map<String, Map<String, Object>> genericApplicationProperties) {
        this.genericApplicationProperties = genericApplicationProperties;
    }
}
