package com.sssio.initiatives.service;

import com.sssio.initiatives.response.Questions;

import java.util.List;

public interface QuestionsService {
    List<Questions> getQuestionsByActivityId(Long activityId);
    List<Questions> getAllQuestions();
}
