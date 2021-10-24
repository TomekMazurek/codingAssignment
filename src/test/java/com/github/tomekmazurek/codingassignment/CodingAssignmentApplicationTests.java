package com.github.tomekmazurek.codingassignment;

import com.github.tomekmazurek.codingassignment.filehandling.ConsoleReader;
import com.github.tomekmazurek.codingassignment.filehandling.FileReader;
import com.github.tomekmazurek.codingassignment.repository.LogRepository;
import com.github.tomekmazurek.codingassignment.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
class CodingAssignmentApplicationTests {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private FileReader fileReader;
    @Autowired
    private ConsoleReader reader;
    @MockBean
    private CommandLineRunner runner;


    @Test
    void shouldLoadApplicationContext() {
        assertThat(logRepository).isNotNull();
        assertThat(logService).isNotNull();
        assertThat(fileReader).isNotNull();
    }
}
