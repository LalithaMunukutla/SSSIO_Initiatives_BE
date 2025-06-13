package com.sssio.initiatives.response;
import com.sssio.initiatives.dto.*;

import java.util.List;

public class Constants {
    private List<ActivityDTO> activities;
    private List<ZoneDTO> zones;
    private List<RegionDTO> regions;
    private List<CountryDTO> countries;
    private List<QuestionTypeDTO> questionTypes;
    private List<RoleDTO> roles;
    private List<ActivityQuestionDTO> questions;

    public List<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }

    public List<ZoneDTO> getZones() {
        return zones;
    }

    public void setZones(List<ZoneDTO> zones) {
        this.zones = zones;
    }

    public List<RegionDTO> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }

    public List<QuestionTypeDTO> getQuestionTypes() {
        return questionTypes;
    }

    public void setQuestionTypes(List<QuestionTypeDTO> questionTypes) {
        this.questionTypes = questionTypes;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public List<ActivityQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ActivityQuestionDTO> questions) {
        this.questions = questions;
    }
}
