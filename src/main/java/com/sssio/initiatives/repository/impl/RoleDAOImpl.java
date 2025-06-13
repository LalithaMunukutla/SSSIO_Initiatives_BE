package com.sssio.initiatives.repository.impl;

import com.sssio.initiatives.dto.RoleDTO;
import com.sssio.initiatives.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> roles = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select role_id, role_name, can_approve from sssio_role where status!= 'DELETED' ");
        try{
            roles = namedParameterJdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
                RoleDTO role = new RoleDTO();
                role.setId(rs.getLong("role_id"));
                role.setRole(rs.getString("role_name"));
                role.setCanApprove(rs.getBoolean("can_approve"));
                return role;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return roles;
    }
}
