package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.QuestionsDAO;
import com.sssio.initiatives.response.Questions;
import com.sssio.initiatives.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsDAO questionsDAO;

    @Override
    public List<Questions> getQuestionsByActivityId(Long activityId) {
        List<Questions> questions = questionsDAO.fetchQuestions(activityId).stream()
                .map(q -> {
                    Questions question = new Questions();
                    question.setId(q.getId());
                    question.setQuestionText(q.getDescription());
                    question.setQuestionType(q.getQuestionType());
                    question.setOptions(q.getOptions().stream()
                            .collect(Collectors.toMap(option -> option.getId(), option -> option.getDescription())));
                    question.setActivityId(activityId);
                    question.setTypeName(q.getTypeName());
                    return question;
                }).collect(Collectors.toList());
        return questions;
    }

    @Override
    public List<Questions> getAllQuestions() {
        List<Questions> questions = questionsDAO.fetchQuestions(null).stream()
                .map(q -> {
                    Questions question = new Questions();
                    question.setId(q.getId());
                    question.setQuestionText(q.getDescription());
                    question.setQuestionType(q.getQuestionType());
                    question.setOptions(q.getOptions().stream()
                            .collect(Collectors.toMap(option -> option.getId(), option -> option.getDescription())));
                    question.setActivityId(q.getActivityId());
                    question.setTypeName(q.getTypeName());
                    return question;
                }).collect(Collectors.toList());
        return questions;
    }
}
