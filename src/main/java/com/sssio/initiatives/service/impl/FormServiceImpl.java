package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.FormDAO;
import com.sssio.initiatives.repository.ResponseDetailsDAO;
import com.sssio.initiatives.request.FormResponses;
import com.sssio.initiatives.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private FormDAO formDAO;

    @Autowired
    private ResponseDetailsDAO responseDetailsDAO;

    @Override
    public Long saveResponse(FormResponses formResponses, String user) {
        Long activityId = formResponses.getActivityId();
        Long zone = formResponses.getZone();
        Long country = formResponses.getCountry();
        Long region = formResponses.getRegion();
        LocalDate date = LocalDate.parse(formResponses.getActivityDate());
        Map<Long, String> answers = formResponses.getAnswers();
        Long responseId = formDAO.saveResponse(activityId, zone, country, region, date, user);
        responseDetailsDAO.saveResponseDetails(responseId, answers, user);
        return responseId;
    }

    @Override
    public void updateResponse(Long responseId, FormResponses formResponses, String user){
        formDAO.updateResponse(responseId, formResponses, user);
        responseDetailsDAO.saveResponseDetails(responseId, formResponses.getAnswers(), user);
    }

    @Override
    public FormResponses getResponse(Long responseId) {
        FormResponses response = formDAO.getResponse(responseId);
        return response;
    }
}
