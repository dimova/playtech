CREATE TABLE configurations (
                                id UUID PRIMARY KEY,
                                service_name VARCHAR(255) NOT NULL,
                                config_key VARCHAR(255) NOT NULL,
                                config_value VARCHAR(255) NOT NULL,
                                created_at TIMESTAMP,
                                updated_at TIMESTAMP
);

CREATE INDEX idx_config_service_name ON configurations(service_name);
