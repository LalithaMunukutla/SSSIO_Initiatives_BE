package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Map<String, Object>> findByEmail(String email) {
        String sql = "SELECT * FROM sssio_user WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, rs -> {
            if (rs.next()) {
                return Optional.of(Map.of(
                        "id", rs.getLong("user_id"),
                        "email", rs.getString("email"),
                        "password", rs.getString("password"),
                        "roleId", rs.getString("role_id"),
                        "userName", rs.getString("user_name")
                ));
            }
            return Optional.empty();
        });
    }

    public int createUser(String email, String password, String username, int country, int zone, int region, int role) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sssio_user ")
                .append("(email, password, user_name, country, zone, region, role_id, ")
                .append("created_by, created_time, mod_by, mod_time, status, version) ")
                .append("values (:email, :password, :username, :country, :zone, :region, :role, ")
                .append(":createdBy, current_timestamp, :modifiedBy, current_timestamp, :status, :version) ");
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("username", username);
        params.put("country", country);
        params.put("zone", zone);
        params.put("region", region);
        params.put("role", role);
        params.put("createdBy", username);
        params.put("modifiedBy", username);
        params.put("status", "active");
        params.put("version", 1);
        return namedParameterJdbcTemplate.update(sb.toString(), params);
    }
}
