package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.repository.ConfigurationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfigurationService {

    private final ConfigurationRepository repository;

    public ConfigurationService(ConfigurationRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "configById", key = "#id")
    public Optional<Configuration> getById(UUID id) {
        return repository.findById(id);
    }

    @Cacheable(value = "configByService", key = "#serviceName")
    public List<Configuration> getByService(String serviceName) {
        return repository.findByServiceName(serviceName);
    }

    @CacheEvict(value = {"configById", "configByService"}, allEntries = true)
    public Configuration save(Configuration config) {
        return repository.save(config);
    }

    @CacheEvict(value = {"configById", "configByService"}, allEntries = true)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}

