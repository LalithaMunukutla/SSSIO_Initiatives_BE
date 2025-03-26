package com.sssio.initiatives.request;

import java.util.Map;

public class FormResponses {
    private Long activityId;
    private Long zone;
    private Long country;
    private Long region;
    private String activityDate;
    private Map<Long, String> answers;
    private String status;

    public FormResponses(Long activityId, Long zone, Long country, Long region, String activityDate, Map<Long, String> answers) {
        this.activityId = activityId;
        this.zone = zone;
        this.country = country;
        this.region = region;
        this.activityDate = activityDate;
        this.answers = answers;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
