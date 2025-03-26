package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.ActivityDAO;
import com.sssio.initiatives.request.ActivityRegistrationReq;
import com.sssio.initiatives.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    @Override
    public String createActivity(ActivityRegistrationReq activityRegistrationReq,
                                 String userDetails) {
        Optional<Map<String, Object>> existingActivity = activityDAO.findByName(activityRegistrationReq.getActivityName());
        if (existingActivity.isPresent()) {
            return "An activity already exists with same name.";
        }
        String name = activityRegistrationReq.getActivityName();
        String description = activityRegistrationReq.getDescription();
        Long superType = activityRegistrationReq.getSuperType();
        activityDAO.createActivity(name, description, superType, userDetails);
        return "Activity created successfully";
    }
}
