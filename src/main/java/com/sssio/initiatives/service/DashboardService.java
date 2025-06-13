package com.sssio.initiatives.service;

import com.sssio.initiatives.response.Dashboard;

import java.util.List;

public interface DashboardService {
    List<Dashboard> getDashboard(String regionIds, String zoneIds, String countryIds, String activityIds);
}
