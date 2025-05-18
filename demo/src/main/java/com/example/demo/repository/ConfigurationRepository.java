package com.example.demo.repository;

import com.example.demo.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {
    List<Configuration> findByServiceName(String serviceName);
}