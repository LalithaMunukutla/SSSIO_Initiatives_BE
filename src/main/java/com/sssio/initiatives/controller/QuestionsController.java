package com.sssio.initiatives.controller;

import com.sssio.initiatives.response.Questions;
import com.sssio.initiatives.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    @GetMapping("form/questions")
    public ResponseEntity<List<Questions>> getQuestions(@RequestParam (value = "activityId", required = false)Long activityId){
        if (activityId != null) {
            return ResponseEntity.ok(questionsService.getQuestionsByActivityId(activityId));
        } else {
            return ResponseEntity.ok(questionsService.getAllQuestions());
        }
    }
}
