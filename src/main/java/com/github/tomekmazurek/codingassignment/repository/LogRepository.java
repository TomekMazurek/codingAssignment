package com.github.tomekmazurek.codingassignment.repository;

import com.github.tomekmazurek.codingassignment.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, String> {

    @Query("select log.id from LogEntity log")
    List<String> getLogsIds();
}
