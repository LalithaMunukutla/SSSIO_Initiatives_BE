package com.sssio.initiatives.repository;

import com.sssio.initiatives.entity.Activity;

import java.util.Map;
import java.util.Optional;

public interface ActivityDAO {
    public Activity fetchActivity(Long activityId);
    public Optional<Map<String, Object>> findByName(String activityName);
    public int createActivity(String activityName, String activityDescription, Long superType, String user);
}
