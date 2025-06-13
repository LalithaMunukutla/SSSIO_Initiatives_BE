package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.FormDAO;
import com.sssio.initiatives.request.FormResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FormDAOImpl implements FormDAO {

    private static final Logger logger = LoggerFactory.getLogger(FormDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long saveResponse(Long activityId, Long zone, Long country, Long region, LocalDate date, String user) {
        
        Long res = 0L;
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sssio_activity_responses (activity_id, activity_date, zone_id, country_id, region_id, ")
                .append("status, version, created_by, created_time, mod_by, mod_time) values ")
                .append("(:activityId, :date, :zone, :country, :region, :status, :version, ")
                .append(":createdBy, CURRENT_TIMESTAMP, :modBy, CURRENT_TIMESTAMP) ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("activityId", activityId);
        params.addValue("date", date);
        params.addValue("zone", zone);
        params.addValue("country", country);
        params.addValue("region", region);
        params.addValue("status", "TO_BE_APPROVED");
        params.addValue("version", 1);
        params.addValue("createdBy", user);
        params.addValue("modBy", user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            logger.info("sql statement: " + sb.toString());
            namedParameterJdbcTemplate.update(sb.toString(), params, keyHolder, new String[]{"response_id"});
            res = keyHolder.getKey().longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void updateResponse(Long responseId, FormResponses formResponses, String user) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE sssio_activity_responses SET activity_id =:activityId, activity_date =:date, ")
                .append("zone_id = :zone, country_id = :country, region_id =:region, ")
                .append("status = :status, version= version+1, mod_by =:modBy, mod_time = CURRENT_TIMESTAMP where response_id = :responseId");
        Map<String, Object> params = new HashMap<>();
        params.put("activityId", formResponses.getActivityId());
        params.put("date", LocalDate.parse(formResponses.getActivityDate()));
        params.put("zone", formResponses.getZone());
        params.put("country", formResponses.getCountry());
        params.put("region", formResponses.getRegion());
        params.put("status", formResponses.getStatus());
        params.put("modBy", user);
        params.put("responseId", responseId);
        try{
            namedParameterJdbcTemplate.update(sb.toString(), params);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public FormResponses getResponse(Long responseId) {
        StringBuilder sb = new StringBuilder();
        FormResponses response = new FormResponses();
        sb.append("SELECT activity_id, activity_date, country_id, region_id, zone_id, status ")
                .append("FROM sssio_activity_responses where response_id = :responseId and status != 'DELETED' ");
        Map<String, Object> params = new HashMap<>();
        params.put("responseId", responseId);
        logger.info("sql to get response: " + sb.toString());
        try{
            return namedParameterJdbcTemplate.queryForObject(sb.toString(), params, (rs, rowNum) -> {
                response.setResponseId(responseId);
                response.setActivityId(rs.getLong("activity_id"));
                response.setZone(rs.getLong("zone_id"));
                response.setCountry(rs.getLong("country_id"));
                response.setRegion(rs.getLong("region_id"));
                response.setActivityDate(rs.getDate("activity_date").toString());
                response.setStatus(rs.getString("status"));
                response.setAnswers(new HashMap<>()); // initialize empty map
                return response;
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<FormResponses> getSubmittedResponses(String userName){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT response_id, activity_id, activity_date, country_id, region_id, zone_id, status ")
                .append("FROM sssio_activity_responses where created_by = :userName and status != 'DELETED' ");
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        logger.info("Getting responses by :" +userName);
        List<FormResponses> responses = new ArrayList<>();
        try{
            responses = namedParameterJdbcTemplate.query(sb.toString(), params, (rs, rowNum) -> {
                FormResponses response = new FormResponses();
                response.setResponseId(rs.getLong("response_id"));
                response.setActivityId(rs.getLong("activity_id"));
                response.setZone(rs.getLong("zone_id"));
                response.setCountry(rs.getLong("country_id"));
                response.setRegion(rs.getLong("region_id"));
                response.setActivityDate(rs.getDate("activity_date").toString());
                response.setStatus(rs.getString("status"));
                response.setAnswers(new HashMap<>()); // initialize empty map
                return response;
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return responses;
    }

    @Override
    public List<FormResponses> getResponsesToBeApproved(String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT response_id, activity_id, activity_date, country_id, region_id, zone_id, status ")
                .append("FROM sssio_activity_responses where status = 'TO_BE_APPROVED' ");
        List<FormResponses> responses = new ArrayList<>();
        try{
            responses = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                FormResponses response = new FormResponses();
                response.setResponseId(rs.getLong("response_id"));
                response.setActivityId(rs.getLong("activity_id"));
                response.setZone(rs.getLong("zone_id"));
                response.setCountry(rs.getLong("country_id"));
                response.setRegion(rs.getLong("region_id"));
                response.setActivityDate(rs.getDate("activity_date").toString());
                response.setStatus(rs.getString("status"));
                response.setAnswers(new HashMap<>()); // initialize empty map
                return response;
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return responses;
    }
}
