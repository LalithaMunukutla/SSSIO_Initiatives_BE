package com.sssio.initiatives.response;

public class Dashboard {
    private Long activityId;
    private String activityName;
    private Long questionId;
    private Long analyticsField;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnalyticsField() {
        return analyticsField;
    }

    public void setAnalyticsField(Long analyticsField) {
        this.analyticsField = analyticsField;
    }
}
