package com.sssio.initiatives.dto;

public class QuestionOptionDTO {
    private Long id;
    private String description;
    private String description2;
    private ActivityQuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public ActivityQuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(ActivityQuestionDTO question) {
        this.question = question;
    }
}
