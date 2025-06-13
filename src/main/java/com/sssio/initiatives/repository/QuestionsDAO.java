package com.sssio.initiatives.repository;

import com.sssio.initiatives.dto.ActivityQuestionDTO;
import com.sssio.initiatives.dto.QuestionOptionDTO;
import com.sssio.initiatives.dto.QuestionTypeDTO;
import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.entity.ActivityQuestion;
import com.sssio.initiatives.entity.ActivityQuestionOption;
import com.sssio.initiatives.entity.ActivityQuestionType;
import com.sssio.initiatives.request.QuestionRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface QuestionsDAO {
    List<ActivityQuestionDTO> fetchQuestions(Long activityId);
    List<QuestionOptionDTO> fetchOptions(Long questionId);
    QuestionTypeDTO fetchType(Long typeId);
    void saveOrUpdateQuestions(Long activityId, List<QuestionRequest> questions, String user);
}
