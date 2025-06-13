package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.ActivityDTO;
import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.repository.ActivityDAO;
import com.sssio.initiatives.repository.QuestionsDAO;
import com.sssio.initiatives.request.ActivityRegistrationReq;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ActivityDAOImpl implements ActivityDAO {

    private static Logger logger = LoggerFactory.getLogger(ActivityDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private QuestionsDAO questionsDAO;

    @Override
    public List<ActivityDTO> getAllActivities(){
        StringBuilder sb = new StringBuilder();
        List<ActivityDTO> activities = new ArrayList<>();
        sb.append("select activity_id, activity_name, activity_supertype, activity_description from sssio_activity where status != 'DELETED' ");
        try{
            activities = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                ActivityDTO a = new ActivityDTO();
                a.setId(rs.getLong("activity_id"));
                a.setActivity(rs.getString("activity_name"));
                a.setActivitySuperType(rs.getLong("activity_supertype"));
                a.setActivityDesc(rs.getString("activity_description"));
                return a;
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public ActivityDTO fetchActivity(Long activityId) {
        String activityQuery = "SELECT activity_id, activity_name, activity_supertype, activity_description from sssio_activity where activity_id = ? and status != 'DELETED'";
        return jdbcTemplate.queryForObject(activityQuery, new Object[]{activityId}, (rs, rowNum) -> {
            ActivityDTO activity = new ActivityDTO();
            activity.setId(rs.getLong("activity_id"));
            activity.setActivity(rs.getString("activity_name"));
            activity.setActivitySuperType(rs.getLong("activity_supertype"));
            activity.setActivityDesc(rs.getString("activity_description"));
            return activity;
        });
    }

    @Override
    public Long saveOrUpdateActivity(ActivityRegistrationReq request, String user){
        StringBuilder sb = new StringBuilder();
        Long activityId = request.getActivityId();
        String activityName = request.getActivityName();
        MapSqlParameterSource params = new MapSqlParameterSource ();
        try{
            if(activityId == null){
                sb.append("INSERT INTO sssio_activity ")
                        .append("(activity_name, activity_description, activity_supertype, ")
                        .append("created_by, created_time, mod_by, mod_time, status, version) ")
                        .append("values (:activityName, :activityDescription, :superType, ")
                        .append(":createdBy, current_timestamp, :modifiedBy, current_timestamp, :status, :version) RETURNING activity_id ");
                params.addValue("activityName", activityName);
                params.addValue("activityDescription", request.getDescription());
                params.addValue("superType", request.getSuperType());
                params.addValue("createdBy", user);
                params.addValue("modifiedBy", user);
                params.addValue("status", "ACTIVE");
                params.addValue("version", 1);
                activityId = namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Long.class);
                questionsDAO.saveOrUpdateQuestions(activityId, request.getQuestions(), user);
            }
            else{
                sb.append("UPDATE sssio_activity set activity_name = :activityName, activity_description = :activityDescription, ")
                        .append("activity_supertype = :superType, version = version+1, mod_by = :modBy, mod_time = CURRENT_TIMESTAMP ")
                        .append("where activity_id = :activityId ");
                params.addValue("activityName", request.getActivityName());
                params.addValue("activityDescription", request.getDescription());
                params.addValue("superType", request.getSuperType());
                params.addValue("modBy", user);
                params.addValue("activityId", activityId);
                namedParameterJdbcTemplate.update(sb.toString(), params);
                questionsDAO.saveOrUpdateQuestions(activityId, request.getQuestions(), user);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return activityId;
    }

    @Override
    public Optional<Map<String, Object>> findByName(String activityName) {
        logger.info("findByName method for activityName: " + activityName);
        String sql = "SELECT * FROM sssio_activity WHERE LOWER(activity_name) = ?";
        if(StringUtils.isNotEmpty(activityName)){
            return jdbcTemplate.query(sql, new Object[]{activityName.toLowerCase()}, rs -> {
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
        return Optional.empty();
    }
}
