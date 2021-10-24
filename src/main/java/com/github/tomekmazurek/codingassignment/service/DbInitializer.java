package com.github.tomekmazurek.codingassignment.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Slf4j
public class DbInitializer {

    private Connection connection;
    private String connectionString;

    public DbInitializer(String connectionString) {
        this.connectionString = connectionString;
    }

    public  void initializeDb() throws Exception {
        log.debug(">> Inside initializeDb method");
        String createLogEntityTable = getCreateTableStatement();
        log.debug(">> Read sql statement:" +createLogEntityTable);
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException exception) {
            log.error(">>Exception thrown: " + exception.getMessage());
            throw exception;
        }

        try {
            log.debug(">> inside try block of initializeDb method");
            connection = DriverManager.getConnection(connectionString, "Sa", "");
            connection.createStatement().executeUpdate(createLogEntityTable);
        } catch (SQLException exception) {
            log.error(">> Error: " + exception.getMessage());
            throw exception;
        } finally {
            log.debug(">> Closing connection");
            connection.close();        }
    }

    private  String getCreateTableStatement() throws IOException {
        return FileUtils.readFileToString(new File("src/main/resources/sql/data.sql"), "UTF-8");
    }
}
