package com.alexsoft.bookstore.config;

import com.alexsoft.bookstore.controller.ConsoleContext;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class AppConfig {
    private static final String CHANGELOGS_PACKAGE = "com.alexsoft.bookstore.changelog";

    @Bean
    public Mongock mongock(MongoProperties mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }

    @Bean
    ConsoleContext consoleContext() {
        return new ConsoleContext(System.out);
    }
}
