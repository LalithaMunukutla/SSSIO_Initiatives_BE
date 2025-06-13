package com.sssio.initiatives.controller;

import com.sssio.initiatives.response.Dashboard;
import com.sssio.initiatives.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("form/filter")
    public ResponseEntity<List<Dashboard>> getDashboard(@RequestParam(value = "regionId", required = false) String regionId,
                                                  @RequestParam(value = "zoneId", required = false) String zoneId,
                                                  @RequestParam(value = "countryId", required = false) String countryId,
                                                  @RequestParam(value = "activityId", required = false) String activityId){
        try {
            List<Dashboard> dashboardData = dashboardService.getDashboard(regionId, zoneId, countryId, activityId);
            if (dashboardData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
