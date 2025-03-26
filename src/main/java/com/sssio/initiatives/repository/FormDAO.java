package com.sssio.initiatives.repository;

import com.sssio.initiatives.request.FormResponses;

import java.time.LocalDate;

public interface FormDAO {
    Long saveResponse(Long activityId, Long zone, Long country, Long region, LocalDate date, String user);
    void updateResponse(Long responseId, FormResponses formResponses, String user);
    FormResponses getResponse(Long responseId);
}
