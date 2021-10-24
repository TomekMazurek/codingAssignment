package com.github.tomekmazurek.codingassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Logs")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LogEntity implements Persistable<String> {

    @Id
    private String id;
    private Long duration;
    private String host;
    private String type;
    private boolean alert;

    @Override
    public boolean isNew() {
        return true;
    }
}
