package com.alexsoft.bookstore.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;

@Configuration
public class BookStoreConfig {

    @Bean
    @Qualifier("Bookstore_CO")
    PrintStream consoleOutput() {
        return System.out;
    }
}
