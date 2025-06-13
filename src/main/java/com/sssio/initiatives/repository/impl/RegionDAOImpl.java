package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.RegionDTO;
import com.sssio.initiatives.repository.RegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegionDAOImpl implements RegionDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<RegionDTO> getRegions() {
        List<RegionDTO> regions = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select region_id, region_name, country_id from sssio_region where status != 'DELETED' ");
        try{
            regions = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                RegionDTO region = new RegionDTO();
                region.setId(rs.getLong("region_id"));
                region.setRegion(rs.getString("region_name"));
                region.setCountry(rs.getLong("country_id"));
                return region;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regions;
    }
}
