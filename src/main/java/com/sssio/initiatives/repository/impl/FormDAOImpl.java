package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.FormDAO;
import com.sssio.initiatives.request.FormResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FormDAOImpl implements FormDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long saveResponse(Long activityId, Long zone, Long country, Long region, LocalDate date, String user) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sssio_activity_responses (activity_id, activity_date, zone_id, country_id, region_id, ")
                .append("status, version, created_by, created_time, mod_by, mod_time) values ")
                .append("(:activityId, :date, :zone, :country, :region, :status, :version, ")
                .append(":createdBy, CURRENT_TIMESTAMP, :modBy, CURRENT_TIMESTAMP) ");
        Map<String, Object> params = new HashMap<>();
        params.put("activityId", activityId);
        params.put("date", date);
        params.put("zone", zone);
        params.put("country", country);
        params.put("region", region);
        params.put("status", "TO_BE_APPROVED");
        params.put("version", 1);
        params.put("createdBy", user);
        params.put("modBy", user);
        return (long) namedParameterJdbcTemplate.update(sb.toString(), params);
    }

    @Override
    public void updateResponse(Long responseId, FormResponses formResponses, String user) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE sssio_activity_responses SET activity_id =:activityId, activity_date =:date, ")
                .append("zone_id = :zone, country_id = :country, region_id =:region, ")
                .append("status = :status, version= version+1, mod_by =:modBy, mod_time = CURRENT_TIMESTAMP ");
        Map<String, Object> params = new HashMap<>();
        params.put("activityId", formResponses.getActivityId());
        params.put("date", LocalDate.parse(formResponses.getActivityDate()));
        params.put("zone", formResponses.getZone());
        params.put("country", formResponses.getCountry());
        params.put("region", formResponses.getRegion());
        params.put("status", formResponses.getStatus());
        params.put("version", 1);
        params.put("createdBy", user);
        params.put("modBy", user);
        namedParameterJdbcTemplate.update(sb.toString(), params);
    }

    @Override
    public FormResponses getResponse(Long responseId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT activity_id, activity_date, country_id, region_id, zone_id ")
                .append("FROM sssio_activity_responses where response_id = :responseId and status != 'DELETED' ");
        List<Object> params = new ArrayList<>();
        params.add(responseId);
        FormResponses response = (FormResponses) jdbcTemplate.query(sb.toString(), params.toArray(), (rs, rowNum) ->
                new FormResponses(
                        rs.getLong("activity_id"),
                        rs.getLong("zone_id"),
                        rs.getLong("country_id"),
                        rs.getLong("region_id"),
                        rs.getDate("activity_date").toString(),
                        new HashMap<Long, String>() // Empty map, to be populated below
                )
        );
        return response;
    }
}
