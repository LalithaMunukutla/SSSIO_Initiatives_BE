package com.sssio.initiatives.repository;

import com.sssio.initiatives.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

public interface UserDAO{
    Optional<Map<String, Object>> findByEmail(String email);
    public int createUser(String email, String password, String username, int country, int zone, int region, int role);
}
