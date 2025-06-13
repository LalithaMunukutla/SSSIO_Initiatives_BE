package com.sssio.initiatives.repository;

import java.util.Map;

public interface ResponseDetailsDAO {
    public void saveResponseDetails(Long responseId, Map<Long, String> answers, String user);
    public void updateResponseDetails(Long responseId, Long activityId, Map<Long, String> answers, String user, String status);
    public Map<Long, String> getResponseDetails(Long responseId);
}
