package com.gralll.taskplanner.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

import static com.gralll.taskplanner.config.Constants.SPRING_PROFILE_DEV;

@Configuration
@EnableJpaRepositories("com.gralll.taskplanner.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile(SPRING_PROFILE_DEV)
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
