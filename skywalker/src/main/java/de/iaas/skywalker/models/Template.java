package de.iaas.skywalker.models;

//@Entity
//@Data
//@NoArgsConstructor
public class Template {
    private String arn;
    private String provider;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
