package com.example.demo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableRetry
public class ConfigurationEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "config-updates";

    public ConfigurationEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Retryable(
            value = { org.apache.kafka.common.KafkaException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2))
    public void publish(String operation, Object configuration) {
        try {
            ConfigurationEvent event = new ConfigurationEvent(operation, (com.example.demo.model.Configuration) configuration);
            String message = objectMapper.writeValueAsString(event);
            log.info("Publishing message to Kafka topic {}: {}", TOPIC, message);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Kafka message", e);
        }
    }
}
