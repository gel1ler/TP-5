package ru.bmstu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("ru.bmstu.studenttokens")
@EnableAspectJAutoProxy
public class AppConfig {
    @Bean
    public Resource csvResource() {
        return new ClassPathResource("students.csv");
    }
}