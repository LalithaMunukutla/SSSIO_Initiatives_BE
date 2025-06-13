package com.sssio.initiatives.service;

import com.sssio.initiatives.repository.UserDAO;
import com.sssio.initiatives.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<Map<String, Object>> authenticateUser(String email, String password) {
        Optional<Map<String, Object>> userOptional = userDAO.findByEmail(email);

        if (userOptional.isPresent()) {
            Map<String, Object> user = userOptional.get();
            String storedPassword = (String) user.get("password");

            if (passwordEncoder.matches(password, storedPassword)) {
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("token", jwtUtil.generateToken(email));
                userDetails.putAll(user);
                return Optional.of(userDetails);
            }
        }
        return Optional.empty();
    }
}
