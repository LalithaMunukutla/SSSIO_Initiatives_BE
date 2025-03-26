package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.repository.ActivityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ActivityDAOImpl implements ActivityDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Activity fetchActivity(Long activityId) {
        String activityQuery = "SELECT activity_id, activity_name, activity_supertype from sssio_activity where activity_id = ? and status != 'DELETED'";
        return jdbcTemplate.queryForObject(activityQuery, new Object[]{activityId}, (rs, rowNum) -> {
            Activity activity = new Activity();
            activity.setId(rs.getLong("activity_id"));
            activity.setName(rs.getString("activity_name"));
            activity.setSuperType(rs.getLong("activity_supertype"));
            return activity;
        });
    }

    @Override
    public int createActivity(String activityName, String activityDescription, Long superType, String user){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sssio_activity ")
                .append("(activity_name, activity_description, activity_supertype, ")
                .append("created_by, created_time, mod_by, mod_time, status, version) ")
                .append("values (:activityName, :activityDescription, :superType, ")
                .append(":createdBy, current_timestamp, :modifiedBy, current_timestamp, :status, :version) ");
        Map<String, Object> params = new HashMap<>();
        params.put("activityName", activityName);
        params.put("activityDescription", activityDescription);
        params.put("superType", superType);
        params.put("createdBy", user);
        params.put("modifiedBy", user);
        params.put("status", "active");
        params.put("version", 1);
        return namedParameterJdbcTemplate.update(sb.toString(), params);
    }

    @Override
    public Optional<Map<String, Object>> findByName(String activityName) {
        String sql = "SELECT * FROM sssio_activity WHERE activity_name = ?";
        return jdbcTemplate.query(sql, new Object[]{activityName}, rs -> {
            if (rs.next()) {
                return Optional.of(Map.of(
                        "id", rs.getLong("activity_id"),
                        "name", rs.getString("activity_name"),
                        "description", rs.getString("activity_description"),
                        "superType", rs.getString("activity_supertype")
                ));
            }
            return Optional.empty();
        });
    }
}
