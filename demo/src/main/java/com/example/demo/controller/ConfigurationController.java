package com.example.demo.controller;

import com.example.demo.kafka.ConfigurationEventPublisher;
import com.example.demo.model.Configuration;
import com.example.demo.service.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/configs")
public class ConfigurationController {

    private final ConfigurationService configService;
    private final ConfigurationEventPublisher eventPublisher;

    public ConfigurationController(ConfigurationService configService,
                                   ConfigurationEventPublisher eventPublisher) {
        this.configService = configService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Configuration> get(@PathVariable UUID id) {
        return configService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Configuration> getByService(@RequestParam String serviceName) {
        return configService.getByService(serviceName);
    }

    @PostMapping
    public Configuration create(@RequestBody Configuration config) {
        config.setId(UUID.randomUUID());
        config.setCreatedAt(Instant.now());
        config.setUpdatedAt(Instant.now());
        Configuration saved = configService.save(config);
        eventPublisher.publish("CREATE", saved);
        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Configuration> update(@PathVariable UUID id, @RequestBody Configuration update) {
        return configService.getById(id)
                .map(existing -> {
                    existing.setKey(update.getKey());
                    existing.setValue(update.getValue());
                    existing.setUpdatedAt(Instant.now());
                    Configuration saved = configService.save(existing);
                    eventPublisher.publish("UPDATE", saved);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        return configService.getById(id)
                .map(config -> {
                    configService.deleteById(id);
                    eventPublisher.publish("DELETE", config);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
