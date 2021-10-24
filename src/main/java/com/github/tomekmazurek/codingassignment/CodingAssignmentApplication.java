package com.github.tomekmazurek.codingassignment;

import com.github.tomekmazurek.codingassignment.dto.LogDto;
import com.github.tomekmazurek.codingassignment.filehandling.ConsoleReader;
import com.github.tomekmazurek.codingassignment.filehandling.FileReader;
import com.github.tomekmazurek.codingassignment.model.LogEntity;
import com.github.tomekmazurek.codingassignment.repository.LogRepository;
import com.github.tomekmazurek.codingassignment.service.DbInitializer;
import com.github.tomekmazurek.codingassignment.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
@Slf4j
public class CodingAssignmentApplication implements CommandLineRunner {

    @Value("${log.default.file}")
    private String DEFAULT_LOGFILE_PATH;
    @Autowired
    private FileReader reader;
    @Autowired
    private LogService logService;
    @Autowired
    private ConsoleReader consoleReader;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        log.info(">> Starting application");
        SpringApplication.run(CodingAssignmentApplication.class, args);
        long endTime = System.currentTimeMillis();
        log.info(">> Application finished in " + (endTime - startTime) + " ms");
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Log> logs = reader.processFile("src/main/resources/logfile.txt");
        System.out.println("Enter path to logfile \n" +
                "(if empty default logfile.txt from \"src/main/resources\" will be processed");
        String providedPath = consoleReader.readFromConsole();

        long startTime = System.currentTimeMillis();
        log.info(">> Reading data from file...");
        List<LogDto> logs = reader.processFile(providedPath.trim().length() == 0 ? DEFAULT_LOGFILE_PATH : providedPath);
        long endTime = System.currentTimeMillis();
        log.info(">> Finished...\n >> Read " + logs.size() + " objects in " + (endTime - startTime) + "ms");
        log.info(">> Processing data...");
        long totalEntitiesInDatabase = logService.processLogsReadFromFile(logs).size();
        log.info(">> Processing finished. {} records exist in database.", totalEntitiesInDatabase);
    }

    public String readFromConsole(Scanner scanner) {
        String line = scanner.nextLine();
        scanner.close();
        return line;
    }
}
