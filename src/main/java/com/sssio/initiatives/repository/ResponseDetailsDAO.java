package com.sssio.initiatives.repository;

import java.util.Map;

public interface ResponseDetailsDAO {
    public void saveResponseDetails(Long responseId, Map<Long, String> answers, String user);
}
