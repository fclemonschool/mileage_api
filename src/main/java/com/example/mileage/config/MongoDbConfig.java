package com.example.mileage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Mongo DB 설정 파일.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.example.mileage.repository.mongo")
public class MongoDbConfig {
}
