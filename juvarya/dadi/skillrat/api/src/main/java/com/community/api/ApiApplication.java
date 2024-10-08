package com.community.api;


import org.broadleafcommerce.common.config.EnableBroadleafSiteRootAutoConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableCaching
//@EnableEurekaClient
public class ApiApplication {
	
    @Configuration
    @EnableBroadleafSiteRootAutoConfiguration
    public static class BroadleafFrameworkConfiguration {}

    public static void main(String[] args) {
    	SpringApplication.run(ApiApplication.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
