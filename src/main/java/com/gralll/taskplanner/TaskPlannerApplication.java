package com.gralll.taskplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskPlannerApplication {

	public static final String SPRING_PROFILE_DEV = "dev";
	public static final String SPRING_PROFILE_PROD = "prod";

	public static void main(String[] args) {
		SpringApplication.run(TaskPlannerApplication.class, args);
	}
}
