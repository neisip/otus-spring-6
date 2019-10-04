package com.alexsoft.bookstore.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.PrintStream;

@Data
@AllArgsConstructor
public class ConsoleContext {
    private PrintStream output;

}
