package com.team2.fithub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.team2.fithub.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:src/main/resources/upload/");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**").allowedMethods("POST", "GET", "PUT", "DELETE");
    }
    
    @Autowired
    private JwtInterceptor jwtInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
    	.excludePathPatterns("/category/**", "/user/**", "/mentor/**", 
    			"/program/**", "/review/**", "/chat/**", "/match/**", "/swagger-ui/**", "/v3/api-docs/**");
    }
}
