package com.microservice.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class KovelaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KovelaServerApplication.class, args);
	}

}
