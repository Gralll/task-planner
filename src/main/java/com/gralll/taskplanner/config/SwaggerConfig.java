/*
 * Copyright 2018: Thomson Reuters. All Rights Reserved. Proprietary and
 * Confidential information of Thomson Reuters. Disclosure, Use or Reproduction
 * without the written authorization of Thomson Reuters is prohibited.
 */
package com.gralll.taskplanner.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes()
                /*.globalOperationParameters(
                Collections.singletonList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Bearer token")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true).build()))*/
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(or(regex("/tasks.*"), regex("/users.*"), regex("/authenticate.*")))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "TaskPlanner REST API",
                "Test application allows you to get users and their tasks",
                "0.0.1",
                StringUtils.EMPTY,
                new Contact("Aleksandr Gruzdev", "https://github.com/AlexandrGruzdev", "aleksandrgruzdev94@gmail.com"),
                "Licence",
                StringUtils.EMPTY,
                Collections.emptyList());
    }
}
