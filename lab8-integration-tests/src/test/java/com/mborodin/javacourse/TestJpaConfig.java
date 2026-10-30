package com.mborodin.javacourse;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфігурація для налаштування in memory бази даних, яка використовується в тестах.
 */
@Configuration
@EntityScan(basePackages = "com.mborodin.javacourse.model")
@EnableJpaRepositories(basePackages = "com.mborodin.javacourse")
@EnableTransactionManagement
public class TestJpaConfig {
}