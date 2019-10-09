package com.alexsoft.bookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("spring.data.mongodb")
public class MongoProperties {
    private int port;
    private String database;
    private String uri;
}
