package com.github.tomekmazurek.codingassignment.helper;

import com.github.tomekmazurek.codingassignment.dto.LogDto;
import com.github.tomekmazurek.codingassignment.model.LogEntity;

import java.util.ArrayList;
import java.util.List;

public class LogEntityMother {

    public static List<LogEntity> createLogEntityListFromPairOfLogs() {
        List<LogDto> logDtoList = LogDtoMother.createPairOfLogDtos();
        List<LogEntity> logEntityList = new ArrayList<>();
        logEntityList.add(
                new LogEntity(
                        logDtoList.get(0).getId(),
                        logDtoList.get(1).getTimestamp() - logDtoList.get(0).getTimestamp(),
                        null,
                        null,
                        logDtoList.get(1).getTimestamp()-logDtoList.get(0).getTimestamp()>4)
        );
        return logEntityList;
    }
}
