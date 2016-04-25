package com.tubing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.tubing.controller.JwtFilter;

@SpringBootApplication
@ImportResource("*-context.xml")
public class TubingApplication {

    public static String NAME = "Tubing";

    @Bean
    public FilterRegistrationBean jwtFilter() {

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/tubing/api/*");

        return registrationBean;
    }

    public static void main(String[] args) {
        
        SpringApplication.run(TubingApplication.class, args);
    }
}
