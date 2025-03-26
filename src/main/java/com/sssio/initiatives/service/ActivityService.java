package com.sssio.initiatives.service;

import com.sssio.initiatives.request.ActivityRegistrationReq;
import org.springframework.security.core.userdetails.UserDetails;

public interface ActivityService {
    public String createActivity(ActivityRegistrationReq activityRegistrationReq, String userDetails);
}
