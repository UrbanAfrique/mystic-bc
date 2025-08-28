package com.mystig.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mystig.backend")
@EnableJpaRepositories("com.mystig.backend.repository")
@EntityScan("com.mystig.backend.model")
@EnableJpaAuditing
public class MystigBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystigBackendApplication.class, args);
    }
}