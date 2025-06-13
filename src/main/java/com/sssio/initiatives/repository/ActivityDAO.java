package com.sssio.initiatives.repository;

import com.sssio.initiatives.dto.ActivityDTO;
import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.request.ActivityRegistrationReq;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ActivityDAO {
    public ActivityDTO fetchActivity(Long activityId);
    public Optional<Map<String, Object>> findByName(String activityName);
    public Long saveOrUpdateActivity(ActivityRegistrationReq activityRegistrationReq, String user);
    public List<ActivityDTO> getAllActivities();
}
