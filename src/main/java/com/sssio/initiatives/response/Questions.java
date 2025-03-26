package com.sssio.initiatives.response;

import java.util.List;
import java.util.Map;

public class Questions {
    private Long id;
    private String questionText;
    private String questionType;
    private Map<Long, String> options;
    private Long activityId;

    public Questions(Long id, String questionText, String questionType, Map<Long, String> options) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
    }

    public Questions(Long id, String questionText, String questionType, Map<Long, String> options, Long activityId) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
        this.activityId = activityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Map<Long, String> getOptions() {
        return options;
    }

    public void setOptions(Map<Long, String> options) {
        this.options = options;
    }
}
