package com.sssio.initiatives.controller;

import com.sssio.initiatives.request.ActivityRegistrationReq;
import com.sssio.initiatives.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("create/activity")
    public ResponseEntity<String> createActivity(@RequestBody ActivityRegistrationReq activityRegistrationReq,
                                                 @RequestParam("userDetails") String userDetails){
        return ResponseEntity.ok(activityService.createActivity(activityRegistrationReq, userDetails));
    }
}
