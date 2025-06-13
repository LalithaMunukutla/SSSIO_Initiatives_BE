package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.entity.User;
import com.sssio.initiatives.repository.UserDAO;
import com.sssio.initiatives.request.UserRegistrationReq;
import com.sssio.initiatives.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String createUser(UserRegistrationReq request) {

        Optional<Map<String, Object>> existingUser = userDAO.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return "User with this email already exists.";
        }
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword()); //Encrypt password
        String username = request.getUserName();
        int country = request.getCountry();
        int zone = request.getZone();
        int region = request.getRegion();
        int role = request.getRole();
        userDAO.createUser(email, password, username, country, zone, region, role);
        return "User account created successfully!";
    }
}
