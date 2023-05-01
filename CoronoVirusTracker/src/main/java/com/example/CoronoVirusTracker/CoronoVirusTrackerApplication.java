package com.example.CoronoVirusTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronoVirusTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronoVirusTrackerApplication.class, args);
	}

}
