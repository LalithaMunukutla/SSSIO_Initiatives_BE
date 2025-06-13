package com.sssio.initiatives.repository;

import com.sssio.initiatives.request.FormResponses;

import java.time.LocalDate;
import java.util.List;

public interface FormDAO {
    Long saveResponse(Long activityId, Long zone, Long country, Long region, LocalDate date, String user);
    void updateResponse(Long responseId, FormResponses formResponses, String user);
    FormResponses getResponse(Long responseId);
    List<FormResponses> getSubmittedResponses(String userName);
    List<FormResponses> getResponsesToBeApproved(String userName);
}
