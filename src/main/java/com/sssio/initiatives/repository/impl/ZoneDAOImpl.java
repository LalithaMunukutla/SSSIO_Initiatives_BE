package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.ZoneDTO;
import com.sssio.initiatives.entity.Zone;
import com.sssio.initiatives.repository.ZoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ZoneDAOImpl implements ZoneDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ZoneDTO> getZones() {
        List<ZoneDTO> zones = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select zone_id, zone_name from sssio_zones where status != 'DELETED' ");
        try{
            zones = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                ZoneDTO zone = new ZoneDTO();
                zone.setId(rs.getLong("zone_id"));
                zone.setZone(rs.getString("zone_name"));
                return zone;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zones;
    }
}
