package com.sssio.initiatives.repository;

import com.sssio.initiatives.response.Dashboard;

import java.util.List;

public interface DashboardDAO {
    List<Dashboard> getDashboard(List<Long> regionIds, List<Long> zoneIds, List<Long> countryIds, List<Long> activityIds);
}
