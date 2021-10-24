package com.github.tomekmazurek.codingassignment.filehandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.codingassignment.dto.LogDto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class FileReaderTest {

    private String filePath = "src/test/resources/temp.txt";
    @Mock
    private File file;
    @InjectMocks
    private FileReader reader;

    @Test
    void shouldProcessFileAndReturnListOfLogDto() throws IOException {
        // given
        int entriesInFile = 600;
        File file = createTmpFile(entriesInFile);

        // when
        List<LogDto> result = reader.processFile(filePath);
        // then
        assertThat(result.size()).isEqualTo(entriesInFile);
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() throws IOException {
        assertThatThrownBy(() -> reader.processFile("tempx.txt")).isInstanceOf(FileNotFoundException.class);
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        Files.deleteIfExists(new File("src/test/resources/temp.txt").toPath());
        Files.deleteIfExists(new File("tempx.txt").toPath());
    }

    private File createTmpFile(int entries) {
        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        Set<LogDto> logs = new HashSet<>();
        long beginningDate = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        long endingDate = Timestamp.valueOf("2020-01-05 00:00:00").getTime();
        long diff = endingDate - beginningDate + 1;
        for (int i = 0; i < entries / 2; i++) {
            long timestamp = beginningDate + (long) (Math.random() * diff);
            String generatedId = RandomStringUtils.randomAlphabetic(10);
            String stateSTARTED = "STARTED";
            String stateFinished = "FINISHED";
            LogDto logDtoStarted = LogDto.builder()
                    .id(generatedId)
                    .state(stateSTARTED)
                    .timestamp(timestamp)
                    .build();
            LogDto logDtoFinished = LogDto.builder()
                    .id(generatedId)
                    .state(stateFinished)
                    .timestamp(timestamp + (long) (Math.random() * 10))
                    .build();
            if (!logs.contains(logDtoStarted.getId())) {
                logs.add(logDtoStarted);
                logs.add(logDtoFinished);
            }
        }
        logs.forEach(logDto -> {
            try {
                FileUtils.write(file, mapper.writeValueAsString(logDto) + "\n", true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        return file;
    }
}