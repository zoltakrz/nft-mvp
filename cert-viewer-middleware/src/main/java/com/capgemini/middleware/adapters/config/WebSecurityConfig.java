package com.capgemini.middleware.adapters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebSecurityConfig implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://certviewer.azurewebsites.net/", "https://nftadmintool.azurewebsites.net/", "http://localhost:3000", "http://localhost:4200")
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}