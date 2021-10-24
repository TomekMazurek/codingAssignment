package com.github.tomekmazurek.codingassignment.filehandling;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader {

    private Scanner scanner;

    public ConsoleReader() {
        this.scanner = new Scanner(System.in);;
    }

    public String readFromConsole() {
        String line = scanner.nextLine();
        scanner.close();
        return line;
    }

}
