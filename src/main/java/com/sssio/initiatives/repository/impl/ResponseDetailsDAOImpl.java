package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.ResponseDetailsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ResponseDetailsDAOImpl implements ResponseDetailsDAO {

    private static final Logger logger = LoggerFactory.getLogger(ResponseDetailsDAOImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveResponseDetails(Long responseId, Map<Long, String> answers, String user) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sssio_activity_responses_detail (response_id, ques_id, response_desc, ")
                .append("version, status, created_by, created_time, mod_by, mod_time) VALUES ")
                .append("(:responseId, :questionId, :response, :version, :status, :createdBy, ")
                .append("CURRENT_TIMESTAMP, :modBy, CURRENT_TIMESTAMP) ")
                .append("ON CONFLICT (response_id, ques_id) ")
                .append("DO UPDATE SET response_desc = EXCLUDED.response_desc, version = (sssio_activity_responses_detail.version::int) + 1, mod_by = EXCLUDED.mod_by, mod_time =CURRENT_TIMESTAMP ");
        List<MapSqlParameterSource> batchArgs = new ArrayList<>();

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("responseId", responseId)
                    .addValue("questionId", entry.getKey())
                    .addValue("response", entry.getValue())
                    .addValue("version", 1)
                    .addValue("status", "ACTIVE")
                    .addValue("createdBy", user)
                    .addValue("modBy", user);
            batchArgs.add(params);
        }
        logger.info("sql statement to save response details: " + sb.toString());
        try{
            namedParameterJdbcTemplate.batchUpdate(sb.toString(), batchArgs.toArray(new MapSqlParameterSource[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateResponseDetails(Long responseId, Long activityId, Map<Long, String> answers, String user, String status) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sb.append("UPDATE sssio_activity_responses_detail set status = 'DELETED', mod_by =:modBy, mod_time = CURRENT_TIMESTAMP, version = version+1 ")
                .append("where response_id = :responseId ");
        if(!status.equals("DELETED")){
            sb.append("and ques_id not in ")
                    .append("(select q.ques_id from sssio_activity_ques q where q.activity_id = :newActivityId and q.status!= 'DELETED' ) ");
            params.put("newActivityId", activityId);
        }
        params.put("modBy", user);
        params.put("responseId", responseId);

        logger.info("sql statement to save response details: " + sb.toString());
        try{
            namedParameterJdbcTemplate.update(sb.toString(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!status.equals("DELETED")){
            saveResponseDetails(responseId, answers, user);
        }
    }

    @Override
    public Map<Long, String> getResponseDetails(Long responseId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ques_id, response_desc from sssio_activity_responses_detail where ")
                .append("response_id = :responseId and STATUS != 'DELETED' ");
        Map<String, Object> params = new HashMap<>();
        params.put("responseId", responseId);
        Map<Long, String> responseDetails = new HashMap<>();
        try{
            responseDetails = namedParameterJdbcTemplate.query(sb.toString(), params, rs -> {
                Map<Long, String> resultMap = new HashMap<>();
                while (rs.next()) {
                    resultMap.put(rs.getLong("ques_id"), rs.getString("response_desc"));
                }
                return resultMap;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDetails;
    }
}
