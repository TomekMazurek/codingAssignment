package com.github.tomekmazurek.codingassignment.service;

import com.github.tomekmazurek.codingassignment.dto.LogDto;
import com.github.tomekmazurek.codingassignment.helper.LogDtoMother;
import com.github.tomekmazurek.codingassignment.helper.LogEntityMother;
import com.github.tomekmazurek.codingassignment.model.LogEntity;
import com.github.tomekmazurek.codingassignment.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class LogServiceTest {

    @Mock
    private LogRepository logRepository;
    @InjectMocks
    private LogService logService;

    @Test
    void shouldCreateLogEntityFromPairOfLogDto() {
        // given
        List<LogDto> pairOfLogDto = LogDtoMother.createPairOfLogDtos();
        Mockito.when(logRepository.findAll()).thenReturn(LogEntityMother.createLogEntityListFromPairOfLogs());
        Mockito.when(logRepository.getLogsIds()).thenReturn(new ArrayList<>());
        Mockito.when(logRepository.saveAll(Mockito.anyCollection())).thenReturn(LogEntityMother.createLogEntityListFromPairOfLogs());
        // when
        List<LogEntity> result = logService.processLogsReadFromFile(pairOfLogDto);
        // then
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(LogEntityMother.createLogEntityListFromPairOfLogs());
    }
}