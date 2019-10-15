package com.alexsoft.bookstore;

import com.alexsoft.bookstore.config.MongoProperties;
import com.alexsoft.bookstore.controller.ConsoleContext;
import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.alexsoft.bookstore.repository")
public class BookstoreApplication {



    public static void main(String[] args) throws Exception {
        SpringApplication.run(BookstoreApplication.class, args);
        //Console.main(args);
    }

}
