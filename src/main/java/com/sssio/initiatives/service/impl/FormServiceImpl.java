package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.FormDAO;
import com.sssio.initiatives.repository.ResponseDetailsDAO;
import com.sssio.initiatives.request.FormResponses;
import com.sssio.initiatives.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class FormServiceImpl implements FormService {

    private static final Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);

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
        logger.info("Saving details of " + activityId + ", " + zone + ", "+ country + ", " + region + ", " + date + ", " + answers);
        Long responseId = formDAO.saveResponse(activityId, zone, country, region, date, user);
        logger.info("Response id: " + responseId);
        responseDetailsDAO.saveResponseDetails(responseId, answers, user);
        return responseId;
    }

    @Override
    public void updateResponse(Long responseId, FormResponses formResponses, String user){
        formDAO.updateResponse(responseId, formResponses, user);
        responseDetailsDAO.updateResponseDetails(responseId, formResponses.getActivityId(), formResponses.getAnswers(), user, formResponses.getStatus());
    }

    @Override
    public FormResponses getResponse(Long responseId) {
        FormResponses response = formDAO.getResponse(responseId);
        logger.info("Getting answers for responseId: " + responseId + " response: " + response);
        response.setAnswers(responseDetailsDAO.getResponseDetails(response.getResponseId()));
        return response;
    }

    @Override
    public List<FormResponses> getSubmittedResponses(String userName){
        List<FormResponses> responses = formDAO.getSubmittedResponses(userName);
        logger.info("Submitted responses by user: "+userName+ " are: " +responses);
        if(!CollectionUtils.isEmpty(responses)){
            responses.forEach(response -> response.setAnswers(responseDetailsDAO.getResponseDetails(response.getResponseId())));
        }
        return responses;
    }

    @Override
    public List<FormResponses> getResponsesToBeApproved(String userName) {
        List<FormResponses> responses = formDAO.getResponsesToBeApproved(userName);
        logger.info("Responses to be approved by admin: "+userName+ " are: " +responses);
        if(!CollectionUtils.isEmpty(responses)){
            responses.forEach(response -> response.setAnswers(responseDetailsDAO.getResponseDetails(response.getResponseId())));
        }
        return responses;
    }
}
