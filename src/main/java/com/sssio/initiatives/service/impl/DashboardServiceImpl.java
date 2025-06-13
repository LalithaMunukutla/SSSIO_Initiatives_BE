package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.DashboardDAO;
import com.sssio.initiatives.response.Dashboard;
import com.sssio.initiatives.service.DashboardService;
import com.sssio.initiatives.utils.GenericUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService{

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private GenericUtils genericUtils;

    @Autowired
    private DashboardDAO dashboardDAO;

    @Override
    public List<Dashboard> getDashboard(String regionId, String zoneId, String countryId, String activityId){
        List<Long> regionIds = genericUtils.StringToLongList(regionId);
        List<Long> zoneIds = genericUtils.StringToLongList(zoneId);
        List<Long> countryIds = genericUtils.StringToLongList(countryId);
        List<Long> activityIds = genericUtils.StringToLongList(activityId);
        logger.info(regionIds.toString() + " " + zoneIds.toString() + " " +
                countryIds.toString() + " " + activityIds.toString());
        return dashboardDAO.getDashboard(regionIds, zoneIds, countryIds, activityIds);
    }
}
