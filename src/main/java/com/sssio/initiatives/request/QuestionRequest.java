package com.sssio.initiatives.request;

import java.util.List;

public class QuestionRequest {
    private Long quesId;
    private String quesDescription;
    private Long quesTypeId;
    private Boolean analyticsNeeded;
    private List<String> options;
    private String status;

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
    }

    public String getQuesDescription() {
        return quesDescription;
    }

    public void setQuesDescription(String quesDescription) {
        this.quesDescription = quesDescription;
    }

    public Long getQuesTypeId() {
        return quesTypeId;
    }

    public void setQuesTypeId(Long quesTypeId) {
        this.quesTypeId = quesTypeId;
    }

    public Boolean getAnalyticsNeeded() {
        return analyticsNeeded;
    }

    public void setAnalyticsNeeded(Boolean analyticsNeeded) {
        this.analyticsNeeded = analyticsNeeded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
