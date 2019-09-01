package com.alexsoft.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BookstoreApplication.class, args);
		Console.main(args);
	}

}
