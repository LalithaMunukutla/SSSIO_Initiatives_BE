package com.sssio.initiatives.request;

import jakarta.validation.constraints.NotBlank;

public class ActivityRegistrationReq {
    @NotBlank
    private String activityName;
    private String description;
    private Long superType;

    public @NotBlank String getActivityName() {
        return activityName;
    }

    public void setActivityName(@NotBlank String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSuperType() {
        return superType;
    }

    public void setSuperType(Long superType) {
        this.superType = superType;
    }
}
