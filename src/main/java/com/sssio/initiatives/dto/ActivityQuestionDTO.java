package com.sssio.initiatives.dto;

import java.util.List;

public class ActivityQuestionDTO {
    private Long id;
    private Long activityId;
    private String description;
    private List<QuestionOptionDTO> options;
    private Long questionType;
    private String typeName;
    private Boolean analyticsNeeded;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionDTO> options) {
        this.options = options;
    }

    public Long getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Long questionType) {
        this.questionType = questionType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getAnalyticsNeeded() {
        return analyticsNeeded;
    }

    public void setAnalyticsNeeded(Boolean analyticsNeeded) {
        this.analyticsNeeded = analyticsNeeded;
    }
}
