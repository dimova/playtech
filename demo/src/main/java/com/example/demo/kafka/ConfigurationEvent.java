package com.example.demo.kafka;

import com.example.demo.model.Configuration;
import lombok.Data;

@Data
public class ConfigurationEvent {
    private String operation; // CREATE, UPDATE, DELETE
    private Configuration configuration;

    public ConfigurationEvent(String operation, Configuration configuration) {
        this.operation = operation;
        this.configuration = configuration;
    }
}

