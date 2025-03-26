package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.ResponseDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ResponseDetailsDAOImpl implements ResponseDetailsDAO {

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
                .append("DO UPDATE SET response_desc = :response, version = version + 1, mod_by =:modBy, mod_time =CURRENT_TIMESTAMP ");
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

        namedParameterJdbcTemplate.batchUpdate(sb.toString(), batchArgs.toArray(new MapSqlParameterSource[0]));
    }
}
