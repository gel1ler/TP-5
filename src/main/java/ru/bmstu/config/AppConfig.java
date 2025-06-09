package ru.bmstu.config;

import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.bmstu")
@EnableWebMvc
@EnableAspectJAutoProxy
public class AppConfig implements WebMvcConfigurer {
    @Bean
    @Primary
    public Resource csvResource() {
        return new ClassPathResource("students.csv");
    }

    @Bean("journalResource")
    public Resource journalResource() {
        return new ClassPathResource("journal.csv");
    }
}