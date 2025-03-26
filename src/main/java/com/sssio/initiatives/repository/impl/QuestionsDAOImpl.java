package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.entity.ActivityQuestion;
import com.sssio.initiatives.entity.ActivityQuestionOption;
import com.sssio.initiatives.entity.ActivityQuestionType;
import com.sssio.initiatives.repository.ActivityDAO;
import com.sssio.initiatives.repository.QuestionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionsDAOImpl implements QuestionsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ActivityDAO activityDAO;

    @Override
    public List<ActivityQuestion> fetchQuestions(Long activityId) {
        String questionQuery = "SELECT q.ques_id, q.ques_description, q.ques_type_id, q.activity_id FROM sssio_activity_ques q where status != 'DELETED' ";
        List<Object> params = new ArrayList<>();

        if (activityId != null) {
            questionQuery += " AND q.activity_id = ?";
            params.add(activityId);
        }

        List<ActivityQuestion> questions = jdbcTemplate.query(questionQuery, params.toArray(), (rs, rowNum) -> {
            ActivityQuestion question = new ActivityQuestion();
            question.setId(rs.getLong("ques_id"));
            question.setDescription(rs.getString("ques_description"));
            question.setQuestionType(fetchType(rs.getLong("ques_type_id")));
            question.setOptions(fetchOptions(rs.getLong("id")));
            question.setActivity(activityDAO.fetchActivity(rs.getLong("activity_id")));
            return question;
        });

        return questions;
    }

    @Override
    public List<ActivityQuestionOption> fetchOptions(Long questionId) {
        String optionQuery = "SELECT option_id, option_description FROM sssio_activity_ques_options WHERE ques_id = ? and status != 'DELETED'";
        List<Object> params = new ArrayList<>();
        params.add(questionId);
        List<ActivityQuestionOption> options = jdbcTemplate.query(optionQuery, params.toArray(), (rs, rowNum) -> {
            ActivityQuestionOption option = new ActivityQuestionOption();
            option.setId(rs.getLong("option_id"));
            option.setDescription(rs.getString("option_description"));
            return option;
        });
        return options;
    }

    @Override
    public ActivityQuestionType fetchType(Long typeId){
        String typeQuery = "SELECT ques_type_id, ques_type from sssio_activity_ques_types where ques_type_id = ? and status != 'DELETED'";
        return jdbcTemplate.queryForObject(typeQuery, new Object[]{typeId}, (rs, rowNum) -> {
            ActivityQuestionType questionType = new ActivityQuestionType();
            questionType.setId(rs.getLong("ques_type_id"));
            questionType.setType(rs.getString("ques_type"));
            return questionType;
        });
    }
}
