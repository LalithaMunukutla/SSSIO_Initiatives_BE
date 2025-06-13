package com.sssio.initiatives.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SSSIO_ACTIVITY_QUES")
public class ActivityQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(name = "ques_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ques_type_id", nullable = false)
    private ActivityQuestionType questionType;

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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ActivityQuestionOption> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityQuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(ActivityQuestionType questionType) {
        this.questionType = questionType;
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

    public List<ActivityQuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<ActivityQuestionOption> options) {
        this.options = options;
    }
}
