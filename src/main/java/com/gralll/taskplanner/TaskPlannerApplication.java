package com.gralll.taskplanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TaskPlannerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(TaskPlannerApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TaskPlannerApplication.class);
        Environment env = app.run(args).getEnvironment();
        String separator = StringUtils.repeat("-", 50);
        LOG.info("\n{}\n\t" +
                        "Application '{}' is run!\n\t" +
                        "URL: \t\thttp://localhost:{}\n\t" +
                        "Profile(s): \t{}\n{}",
                separator,
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getActiveProfiles(),
                separator);
    }
}