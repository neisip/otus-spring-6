package com.alexsoft.bookstore.config;

import com.alexsoft.bookstore.controller.ConsoleContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookStoreConfig {

    @Bean
    ConsoleContext consoleContext() {
        return new ConsoleContext(System.out);
    }
}
