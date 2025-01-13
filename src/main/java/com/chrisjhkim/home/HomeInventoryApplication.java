package com.chrisjhkim.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomeInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeInventoryApplication.class, args);
	}

}
