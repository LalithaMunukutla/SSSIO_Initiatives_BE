package com.sssio.initiatives.service;

import com.sssio.initiatives.request.FormResponses;

import java.util.Map;

public interface FormService {
    Long saveResponse(FormResponses formResponses, String user);
    void updateResponse(Long responseId, FormResponses formResponses, String user);
    FormResponses getResponse(Long responseId);
}
