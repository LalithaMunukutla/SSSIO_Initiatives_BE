package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.QuestionsDAO;
import com.sssio.initiatives.response.Questions;
import com.sssio.initiatives.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsDAO questionsDAO;

    @Override
    public List<Questions> getQuestionsByActivityId(Long activityId) {
        return questionsDAO.fetchQuestions(activityId)
                .stream()
                .map(q -> new Questions(q.getId(), q.getDescription(), q.getQuestionType().getType(),
                        q.getOptions().stream().collect(Collectors.toMap(option -> option.getId(), option -> option.getDescription())), activityId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Questions> getAllQuestions() {
        return questionsDAO.fetchQuestions(null)
                .stream()
                .map(q -> new Questions(q.getId(), q.getDescription(), q.getQuestionType().getType(),
                        q.getOptions().stream().collect(Collectors.toMap(option -> option.getId(), option -> option.getDescription()))))
                .collect(Collectors.toList());
    }
}
