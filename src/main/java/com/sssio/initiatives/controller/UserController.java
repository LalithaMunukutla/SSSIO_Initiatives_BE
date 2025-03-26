package com.sssio.initiatives.controller;

import com.sssio.initiatives.request.UserRegistrationReq;
import com.sssio.initiatives.service.AuthService;
import com.sssio.initiatives.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("user/registration")
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationReq userRegistrationReq){
        return ResponseEntity.ok(userService.createUser(userRegistrationReq));
    }

    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<String> token = authService.authenticateUser(request.get("email"), request.get("password"));

        if (token.isPresent()) {
            return ResponseEntity.ok(Map.of("token", token.get()));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

}
