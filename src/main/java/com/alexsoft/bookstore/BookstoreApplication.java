package com.alexsoft.bookstore;

import com.alexsoft.bookstore.controller.ConsoleContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BookstoreApplication.class, args);
		Console.main(args);
	}


	@Bean
	ConsoleContext consoleContext() {
		return new ConsoleContext(System.out);
	}
}
