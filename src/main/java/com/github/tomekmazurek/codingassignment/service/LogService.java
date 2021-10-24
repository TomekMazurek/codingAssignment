package com.github.tomekmazurek.codingassignment.service;

import com.github.tomekmazurek.codingassignment.dto.LogDto;
import com.github.tomekmazurek.codingassignment.model.LogEntity;
import com.github.tomekmazurek.codingassignment.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LogService {

    private LogRepository logRepository;

    public LogService(LogRepository logRepository) throws Exception {
        this.logRepository = logRepository;
    }

    public List<LogEntity> processLogsReadFromFile(List<LogDto> logDtoListDto) {
        log.debug(">> Inside processLogsReadFromFile");
        Map<String, LogEntity> convertedLogEntities = sortLogsAndConvertToMap(logDtoListDto);
        saveLogs(filterLogsThatDoesNotExistInDb(convertedLogEntities));
        return getAllLogs();
    }

    private void saveLogs(Map<String, LogEntity> logEntities) {
        log.debug(">> Inside saveLogs method");
        log.info(">> Saving logs to database");
        logRepository.saveAll(logEntities.values());
//        logEntities.forEach((s, logEntity) -> logRepository.save(logEntity));
        log.info(">> Completed saving logs to database");
    }

    private Map<String, LogEntity> sortLogsAndConvertToMap(List<LogDto> logDtos) {
        log.info(">> Sorting Logs");
        logDtos.sort(Comparator.comparing(LogDto::getId));
        log.info(">> Logs sorted");
        Map<String, LogEntity> logEntities = new HashMap<>();
        while (logDtos.size() >= 2) {
            LogEntity logEntity = createLogEntityFromPairOfLogs(logDtos.get(0), logDtos.get(1));
            logEntities.put(logEntity.getId(), logEntity);
            logDtos.remove(1);
            logDtos.remove(0);
            log.debug(">> LogEntity created, removed written objects form logDto list, now list size: {}",logDtos.size());
        }
        log.info(">> Entities created: {} total", logEntities.size());
        return logEntities;
    }

    private List<LogEntity> getAllLogs() {
        log.debug(">> Inside getAllLogs method ");
        List<LogEntity> logs = logRepository.findAll();
        log.info(">> " + logs.size() + " logs exist in database");
        return logs;
    }

    private Map<String, LogEntity> filterLogsThatDoesNotExistInDb(Map<String, LogEntity> logEntityMap) {
        int sizeBeforeFiltering = logEntityMap.size();
        log.debug(">> Inside filterLogsThatDoesNotExistInDb");
        log.info(">> Checking if Logs exist in database");
        List<String> existingLogs = logRepository.getLogsIds();
        log.debug(">> Filtering existing logs started");
        for (String id : existingLogs) {
            logEntityMap.remove(id);
        }
        log.info(">>{} of LogEntities read from file exist in database, {} entities will be stored",(sizeBeforeFiltering-logEntityMap.size()), logEntityMap.size());
        return logEntityMap;
    }

    private LogEntity createLogEntityFromPairOfLogs(LogDto firstLog, LogDto secondLog) {
        log.debug(">> Inside createLogEntityFromPairOfLogs");
        LogEntity entity = new LogEntity();
        if (firstLog.getState().equals("FINISHED")) {
            log.debug(">> first log has state FINISHED");
            entity.setDuration(firstLog.getTimestamp() - secondLog.getTimestamp());
        } else {
            log.debug(">> second log has state FINISHED");
            entity.setDuration(secondLog.getTimestamp() - firstLog.getTimestamp());
        }
        entity.setId(firstLog.getId());
        entity.setAlert(entity.getDuration() > 4 ? true : false);
        entity.setHost(firstLog.getHost());
        entity.setType(firstLog.getType());
        log.debug(">> Created LogEntity: " + entity.toString());
        return entity;
    }
}
