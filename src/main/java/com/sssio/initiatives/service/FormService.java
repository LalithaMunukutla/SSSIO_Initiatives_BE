package com.sssio.initiatives.service;

import com.sssio.initiatives.request.FormResponses;

import java.util.List;
import java.util.Map;

public interface FormService {
    Long saveResponse(FormResponses formResponses, String user);
    void updateResponse(Long responseId, FormResponses formResponses, String user);
    FormResponses getResponse(Long responseId);
    List<FormResponses> getSubmittedResponses(String userName);
    List<FormResponses> getResponsesToBeApproved(String userName);
}
