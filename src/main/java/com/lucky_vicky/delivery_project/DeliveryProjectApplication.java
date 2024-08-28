package com.lucky_vicky.delivery_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DeliveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryProjectApplication.class, args);
	}

}
