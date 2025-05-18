package com.example.configclient.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ConfigurationEvent {
    private String operation;
    private Configuration configuration;

    @Data
    public static class Configuration {
        private UUID id;
        private String serviceName;
        private String key;
        private String value;
        private Instant createdAt;
        private Instant updatedAt;
    }
}
