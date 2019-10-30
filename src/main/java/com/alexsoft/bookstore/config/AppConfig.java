package com.alexsoft.bookstore.config;

import com.alexsoft.bookstore.controller.ConsoleContext;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableConfigurationProperties
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.alexsoft.bookstore.repository")
public class AppConfig implements WebFluxConfigurer {
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

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

