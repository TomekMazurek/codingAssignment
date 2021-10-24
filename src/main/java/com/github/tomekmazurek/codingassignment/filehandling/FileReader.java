package com.github.tomekmazurek.codingassignment.filehandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.codingassignment.dto.LogDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class FileReader {

    private List<LogDto> logs;
    private ObjectMapper mapper;

    public List<LogDto> processFile(String filename) throws IOException {
        log.debug(">> Inside processFile method");
        log.info(">> Trying to read file: " + filename);
        File file = new File(filename);
        log.info(">> File found " + file.getAbsolutePath());
        LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
        log.info(">> Processing the file");
        while (lineIterator.hasNext()) {
            LogDto tmpLog = mapper.readValue(lineIterator.nextLine(), LogDto.class);
            logs.add(tmpLog);
            log.debug(">> Log added to list. Now list containing: " + logs.size() + " objects");

        }
        log.info(">> Processing completed. Found " + logs.size() + " logs");
        return logs;
    }

    @Autowired
    public FileReader() {
        log.debug(">>Inside FileReader constructor");
        this.logs = new LinkedList<>();
        this.mapper = new ObjectMapper();
    }
}
