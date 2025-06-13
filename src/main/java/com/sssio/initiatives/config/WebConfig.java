package com.sssio.initiatives.config;

import com.sssio.initiatives.filter.JwtUserDetailsFilter;
import com.sssio.initiatives.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private final JwtUtil jwtUtil;

    public WebConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public FilterRegistrationBean<JwtUserDetailsFilter> jwtFilter() {
        FilterRegistrationBean<JwtUserDetailsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtUserDetailsFilter(jwtUtil));
        registrationBean.addUrlPatterns("/*"); // Apply to your API endpoints
        return registrationBean;
    }
}
