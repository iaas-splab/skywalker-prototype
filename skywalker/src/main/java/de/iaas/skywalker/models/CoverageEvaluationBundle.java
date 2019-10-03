package de.iaas.skywalker.models;

public class CoverageEvaluationBundle {
    private GenericApplicationModel gam;
    private String targetPlatformId;

    public GenericApplicationModel getGam() {
        return gam;
    }

    public void setGam(GenericApplicationModel gam) {
        this.gam = gam;
    }

    public String getTargetPlatformId() {
        return targetPlatformId;
    }

    public void setTargetPlatformId(String targetPlatformId) {
        this.targetPlatformId = targetPlatformId;
    }
}
