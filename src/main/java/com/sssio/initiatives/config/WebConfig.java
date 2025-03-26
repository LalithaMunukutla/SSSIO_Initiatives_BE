package com.sssio.initiatives.config;

import com.sssio.initiatives.filter.JwtUserDetailsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<JwtUserDetailsFilter> jwtFilter() {
        FilterRegistrationBean<JwtUserDetailsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtUserDetailsFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to your API endpoints
        return registrationBean;
    }
}
