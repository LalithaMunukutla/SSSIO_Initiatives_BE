package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.QuestionTypeDTO;
import com.sssio.initiatives.repository.QuestionTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionTypeDAOImpl implements QuestionTypeDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<QuestionTypeDTO> getAllQuestionTypes() {
        List<QuestionTypeDTO> questionTypes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select ques_type_id, ques_type from sssio_activity_ques_types where status != 'DELETED' ");
        try {
            questionTypes = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                QuestionTypeDTO questionType = new QuestionTypeDTO();
                questionType.setId(rs.getLong("ques_type_id"));
                questionType.setType(rs.getString("ques_type"));
                return questionType;
            }) ;
        }catch(Exception e){
            e.printStackTrace();
        }
        return questionTypes;
    }
}
