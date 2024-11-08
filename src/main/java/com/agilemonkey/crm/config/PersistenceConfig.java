package com.agilemonkey.crm.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.agilemonkey.crm.repository")
public class PersistenceConfig {
}
