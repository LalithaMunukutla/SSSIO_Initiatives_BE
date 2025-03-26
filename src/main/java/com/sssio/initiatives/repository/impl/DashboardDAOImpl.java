package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.DashboardDAO;
import com.sssio.initiatives.response.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardDAOImpl implements DashboardDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Dashboard> getDashboard(List<Long> regionIds, List<Long> zoneIds, List<Long> countryIds, List<Long> activityIds) {
        StringBuilder sb = new StringBuilder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        sb.append("select r.activity_id, a.activity_name, q.ques_id, ")
                .append("sum(case when rd.response_desc ~ '^-?\\d+$' THEN cast(rd.response_desc as BIGINT) ELSE 0 END) as summation ")
                .append("from sssio_activity_responses_detail rd ")
                .append("join sssio_activity_responses r on r.response_id = rd.response_id ")
                .append("join sssio_activity_ques q on rd.ques_id = q.ques_id ")
                .append("join sssio_activity a on a.activity_id = r.activity_id ")
                .append("where q.analytics_needed = true and r.status = 'APPROVED' ");
        if(regionIds != null){
            sb.append("and r.region_id in (:regionIds) ");
            params.addValue("regionIds", regionIds);
        }
        if(zoneIds != null){
            sb.append("and r.zone_id in (:zoneIds) ");
            params.addValue("zoneIds", zoneIds);
        }
        if(countryIds != null){
            sb.append("and r.country_id in (:countryIds) ");
            params.addValue("countryIds", countryIds);
        }
        if(activityIds != null){
            sb.append("and r.activity_id in (:activityIds) ");
            params.addValue("activityIds", activityIds);
        }
        sb.append("GROUP BY r.activity_id, a.activity_name, q.ques_id ")
                .append("ORDER BY r.activity_id ");
        return namedParameterJdbcTemplate.query(sb.toString(), params, (rs, rowNum) -> {
            Dashboard d = new Dashboard();
            d.setActivityId(rs.getLong("activity_id"));
            d.setActivityName(rs.getString("activity_name"));
            d.setQuestionId(rs.getLong("ques_id"));
            d.setAnalyticsField(rs.getLong("summation"));
            return d;
        });
    }
}
