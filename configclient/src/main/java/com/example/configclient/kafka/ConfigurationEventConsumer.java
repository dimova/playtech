package com.example.configclient.kafka;

import com.example.configclient.model.ConfigurationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigurationEventConsumer {

    private final ObjectMapper objectMapper;

    public ConfigurationEventConsumer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "config-updates", groupId = "config-client-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            ConfigurationEvent event = objectMapper.readValue(record.value(), ConfigurationEvent.class);
            log.info("Received config event: operation={}, config={}", event.getOperation(), event.getConfiguration());
        } catch (Exception e) {
            log.error("Failed to process Kafka message", e);
        }
    }
}

