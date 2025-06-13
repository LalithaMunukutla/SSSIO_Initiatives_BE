package com.sssio.initiatives.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ActivityRegistrationReq {
    private Long activityId;

    @NotBlank
    private String activityName;
    private String description;
    private Long superType;
    private List<QuestionRequest> questions;

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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }
}
