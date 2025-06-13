package com.sssio.initiatives.dto;

public class ActivityDTO {
    private Long id;
    private String activity;
    private String activityDesc;
    private Long activitySuperType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public Long getActivitySuperType() {
        return activitySuperType;
    }

    public void setActivitySuperType(Long activitySuperType) {
        this.activitySuperType = activitySuperType;
    }
}
