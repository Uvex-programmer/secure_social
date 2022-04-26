package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SocialPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialPlatformApplication.class, args);
	}

}
