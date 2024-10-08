package com.juvarya.kovela.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@ComponentScan(basePackages = { "com.juvarya.kovela.customer", "com.juvarya.kovela.security" })
public class KovelaCustomerApp {

	public static void main(String[] args) {
		SpringApplication.run(KovelaCustomerApp.class, args);
	}

}
