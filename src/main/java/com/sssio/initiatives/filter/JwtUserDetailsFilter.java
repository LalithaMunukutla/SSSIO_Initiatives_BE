package com.sssio.initiatives.filter;

import com.sssio.initiatives.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@WebFilter("/*")
public class JwtUserDetailsFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsFilter.class);
    @Autowired
    private JwtUtil jwtUtil;

    public JwtUserDetailsFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void destroy() {
        // Clean up if needed
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extract JWT token from Authorization header
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix

            // Extract user details from the token
            String username = jwtUtil.extractEmail(token);
            logger.info("Token for user - " + username + " is: " + token);
            // Store user details in the request (you can set it as an attribute)
            request.setAttribute("userDetails", username); // You can customize this as per your requirements
        }

        filterChain.doFilter(request, response);
    }
}
