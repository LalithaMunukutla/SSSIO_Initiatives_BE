package com.sssio.initiatives.filter;

import com.sssio.initiatives.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class JwtUserDetailsFilter implements Filter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization (if needed)
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Extract JWT token from Authorization header
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix

            // Extract user details from the token
            String username = jwtUtil.extractEmail(token);

            // Store user details in the request (you can set it as an attribute)
            httpRequest.setAttribute("userDetails", username); // You can customize this as per your requirements
        }

        filterChain.doFilter(servletRequest, servletResponse); // Continue the request-response cycle
    }

    @Override
    public void destroy() {
        // Clean up if needed
    }
}
