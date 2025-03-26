package com.sssio.initiatives.service;

import com.sssio.initiatives.repository.UserDAO;
import com.sssio.initiatives.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Optional<String> authenticateUser(String email, String password) {
        Optional<Map<String, Object>> userOptional = userDAO.findByEmail(email);

        if (userOptional.isPresent()) {
            Map<String, Object> user = userOptional.get();
            String storedPassword = (String) user.get("password");

            if (passwordEncoder.matches(password, storedPassword)) {
                return Optional.of(jwtUtil.generateToken(email));
            }
        }
        return Optional.empty();
    }
}
