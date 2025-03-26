package com.sssio.initiatives.repository;

import com.sssio.initiatives.entity.Activity;
import com.sssio.initiatives.entity.ActivityQuestion;
import com.sssio.initiatives.entity.ActivityQuestionOption;
import com.sssio.initiatives.entity.ActivityQuestionType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface QuestionsDAO {
    List<ActivityQuestion> fetchQuestions(Long activityId);
    List<ActivityQuestionOption> fetchOptions(Long questionId);
    ActivityQuestionType fetchType(Long typeId);
}
