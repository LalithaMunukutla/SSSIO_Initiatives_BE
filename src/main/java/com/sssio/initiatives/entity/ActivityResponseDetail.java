package com.sssio.initiatives.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "SSSIO_ACTIVITY_RESPONSES_DETAIL")
public class ActivityResponseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selection_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "response_id", nullable = false)
    private ActivityResponse response;

    @ManyToOne
    @JoinColumn(name = "ques_id", nullable = false)
    private ActivityQuestion question;

    @Column(name = "response_desc")
    private String responseDescription;

    @Column(name = "version")
    private String version;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "mod_by")
    private String modifiedBy;

    @Column(name = "mod_time")
    private LocalDateTime modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityResponse getResponse() {
        return response;
    }

    public void setResponse(ActivityResponse response) {
        this.response = response;
    }

    public ActivityQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ActivityQuestion question) {
        this.question = question;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
