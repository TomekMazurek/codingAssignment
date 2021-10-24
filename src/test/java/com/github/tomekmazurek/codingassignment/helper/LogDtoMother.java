package com.github.tomekmazurek.codingassignment.helper;

import com.github.tomekmazurek.codingassignment.dto.LogDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDtoMother {

    public static List<LogDto> createPairOfLogDtos() {
        List<LogDto> list = new ArrayList<>();
        list.add(LogDto.builder()
                .id("scshmbta")
                .state("STARTED")
                .timestamp(Timestamp.valueOf(LocalDateTime.now()).getTime())
                .build());
        list.add(LogDto.builder()
                .id("scshmbta")
                .state("FINISHED")
                .timestamp(Timestamp.valueOf(LocalDateTime.now()).getTime() + 2)
                .build());
        return list;
    }

}
