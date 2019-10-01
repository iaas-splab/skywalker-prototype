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
public class ServiceProperty {

    @Id
    @GeneratedValue
    private Long id;

    private @NonNull
    String genericResourceName;

    private @NonNull
    String platform;

    private @NonNull
    String property;

    private @NonNull
    String genericPropertyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getGenericResourceName() {
        return genericResourceName;
    }

    public void setGenericResourceName(@NonNull String genericResourceName) {
        this.genericResourceName = genericResourceName;
    }

    @NonNull
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(@NonNull String platform) {
        this.platform = platform;
    }

    @NonNull
    public String getProperty() {
        return property;
    }

    public void setProperty(@NonNull String property) {
        this.property = property;
    }

    @NonNull
    public String getGenericPropertyName() {
        return genericPropertyName;
    }

    public void setGenericPropertyName(@NonNull String genericPropertyName) {
        this.genericPropertyName = genericPropertyName;
    }
}
