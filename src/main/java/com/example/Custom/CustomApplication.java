package com.example.Custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class CustomApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomApplication.class, args);
	}

}
