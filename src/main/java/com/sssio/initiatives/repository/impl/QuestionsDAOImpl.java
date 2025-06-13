package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.ActivityQuestionDTO;
import com.sssio.initiatives.dto.QuestionOptionDTO;
import com.sssio.initiatives.dto.QuestionTypeDTO;
import com.sssio.initiatives.repository.QuestionsDAO;
import com.sssio.initiatives.request.QuestionRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class QuestionsDAOImpl implements QuestionsDAO {

    private static Logger logger = LoggerFactory.getLogger(QuestionsDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ActivityQuestionDTO> fetchQuestions(Long activityId) {
        String questionQuery = "SELECT q.ques_id, q.ques_description, q.ques_type_id, q.activity_id, qt.ques_type, q.analytics_needed FROM sssio_activity_ques q join sssio_activity_ques_types qt on qt.ques_type_id = q.ques_type_id where q.status != 'DELETED' and qt.status != 'DELETED' ";
        List<Object> params = new ArrayList<>();

        if (activityId != null) {
            questionQuery += " AND q.activity_id = ?";
            params.add(activityId);
        }

        List<ActivityQuestionDTO> questions = jdbcTemplate.query(questionQuery, params.toArray(), (rs, rowNum) -> {
            ActivityQuestionDTO question = new ActivityQuestionDTO();
            question.setId(rs.getLong("ques_id"));
            question.setDescription(rs.getString("ques_description"));
            question.setQuestionType(rs.getLong("ques_type_id"));
            question.setOptions(fetchOptions(rs.getLong("ques_id")));
            question.setActivityId(rs.getLong("activity_id"));
            question.setTypeName(rs.getString("ques_type"));
            question.setAnalyticsNeeded(rs.getBoolean("analytics_needed"));
            return question;
        });

        return questions;
    }

    @Override
    public List<QuestionOptionDTO> fetchOptions(Long questionId) {
        String optionQuery = "SELECT option_id, option_description FROM sssio_activity_ques_options WHERE ques_id = ? and status != 'DELETED'";
        List<Object> params = new ArrayList<>();
        params.add(questionId);
        List<QuestionOptionDTO> options = jdbcTemplate.query(optionQuery, params.toArray(), (rs, rowNum) -> {
            QuestionOptionDTO option = new QuestionOptionDTO();
            option.setId(rs.getLong("option_id"));
            option.setDescription(rs.getString("option_description"));
            return option;
        });
        return options;
    }

    @Override
    public QuestionTypeDTO fetchType(Long typeId){
        String typeQuery = "SELECT ques_type_id, ques_type from sssio_activity_ques_types where ques_type_id = ? and status != 'DELETED'";
        return jdbcTemplate.queryForObject(typeQuery, new Object[]{typeId}, (rs, rowNum) -> {
            QuestionTypeDTO questionType = new QuestionTypeDTO();
            questionType.setId(rs.getLong("ques_type_id"));
            questionType.setType(rs.getString("ques_type"));
            return questionType;
        });
    }

    @Override
    public void saveOrUpdateQuestions(Long activityId, List<QuestionRequest> questions, String user) {

        long analyticsCountInRequest = questions.stream()
                .filter(q -> Boolean.TRUE.equals(q.getAnalyticsNeeded()))
                .count();
        if (analyticsCountInRequest > 1)
            throw new IllegalArgumentException("Only one question can have analytics_needed = true.");
        try{
            if (analyticsCountInRequest == 1) {
                // Check if any other existing question has analytics_needed = true
                List<Long> existingIds = questions.stream()
                        .map(QuestionRequest::getQuesId)
                        .filter(Objects::nonNull)
                        .toList();

                String checkExistingAnalyticsSql = "SELECT COUNT(*) FROM sssio_activity_ques WHERE activity_id = :activityId "
                        + "AND analytics_needed = true AND status != 'DELETED' "
                        + (existingIds.isEmpty() ? "" : "AND ques_id NOT IN (:ids)");

                MapSqlParameterSource analyticsParams = new MapSqlParameterSource()
                        .addValue("activityId", activityId);
                if (!existingIds.isEmpty()) analyticsParams.addValue("ids", existingIds);

                int existingCount = Objects.requireNonNull(namedParameterJdbcTemplate.queryForObject(checkExistingAnalyticsSql, analyticsParams, Integer.class));
                if (existingCount > 0) {
                    throw new IllegalArgumentException("Only one question can have analytics_needed = true.");
                }
            }
            List<QuestionRequest> insertList = new ArrayList<>();
            List<QuestionRequest> updateList = new ArrayList<>();

            for (QuestionRequest q : questions) {
                if (q.getQuesId() == null)
                    insertList.add(q);
                else
                    updateList.add(q);
            }
            if (!insertList.isEmpty()) {
                String insertSql = "INSERT INTO sssio_activity_ques (activity_id, ques_description, ques_type_id, analytics_needed, status, version, created_by, created_time, mod_by, mod_time) "
                        + "VALUES (:activityId, :desc, :typeId, :analytics, 'ACTIVE', 1, :createdBy, CURRENT_TIMESTAMP, :modBy, CURRENT_TIMESTAMP)";

                SqlParameterSource[] batchParams = insertList.stream().map(q ->
                        new MapSqlParameterSource()
                                .addValue("activityId", activityId)
                                .addValue("desc", q.getQuesDescription())
                                .addValue("typeId", q.getQuesTypeId())
                                .addValue("analytics", q.getAnalyticsNeeded())
                                .addValue("createdBy", user)
                                .addValue("modBy", user)
                ).toArray(SqlParameterSource[]::new);
                logger.info("Running insert query on questions for : " + insertList.size());
                namedParameterJdbcTemplate.batchUpdate(insertSql, batchParams);
            }
            if (!updateList.isEmpty()) {
                String updateSql = "UPDATE sssio_activity_ques SET ques_description = :desc, ques_type_id = :typeId, "
                        + "analytics_needed = :analytics, status = :status, version = version + 1, mod_by = :modBy, mod_time = CURRENT_TIMESTAMP "
                        + "WHERE ques_id = :quesId";

                SqlParameterSource[] updateParams = updateList.stream().map(q ->
                        new MapSqlParameterSource()
                                .addValue("desc", q.getQuesDescription())
                                .addValue("typeId", q.getQuesTypeId())
                                .addValue("analytics", q.getAnalyticsNeeded())
                                .addValue("modBy", user)
                                .addValue("quesId", q.getQuesId())
                                .addValue("status", q.getStatus())
                ).toArray(SqlParameterSource[]::new);
                logger.info("Running update query on questions for : " + updateList.size());
                namedParameterJdbcTemplate.batchUpdate(updateSql, updateParams);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
