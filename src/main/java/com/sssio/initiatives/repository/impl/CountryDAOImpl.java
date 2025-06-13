package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.CountryDTO;
import com.sssio.initiatives.repository.CountryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CountryDTO> getCountries() {
        StringBuilder sb = new StringBuilder();
        List<CountryDTO> countries = new ArrayList<>();
        sb.append("select country_id, country_name, zone_id from sssio_countries where status != 'DELETED' ");
        try{
            countries = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                CountryDTO country = new CountryDTO();
                country.setCountry(rs.getString("country_name"));
                country.setId(rs.getLong("country_id"));
                country.setZone(rs.getLong("zone_id"));
                return country;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }
}
