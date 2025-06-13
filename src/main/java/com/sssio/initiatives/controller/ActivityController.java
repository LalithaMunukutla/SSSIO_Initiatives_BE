package com.sssio.initiatives.controller;

import com.sssio.initiatives.request.ActivityRegistrationReq;
import com.sssio.initiatives.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("saveOrUpdate/activity")
    public ResponseEntity<String> createActivity(@RequestBody ActivityRegistrationReq activityRegistrationReq,
                                                 @RequestParam("userDetails") String userDetails){
        try{
            return ResponseEntity.ok(activityService.saveOrUpdateActivity(activityRegistrationReq, userDetails));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
