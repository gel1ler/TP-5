package ru.bmstu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {
                "ru.bmstu.controllers",
                "org.springdoc"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfig.class)
        }
)
public class WebConfig implements WebMvcConfigurer {
    //Преобразование типов данных в контроллерах
    @Bean(name = "mvcConversionService")
    public FormattingConversionService mvcConversionService() {
        return new FormattingConversionService();
    }

    //По умолчанию возвращает JSON
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    //Настройка путей для доступа к статическим ресурсам
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler("/webjars/")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false);

        registry.addResourceHandler("/v3/api-docs/")
                .addResourceLocations("classpath:/META-INF/resources/")
                .resourceChain(false);
    }

    //Перенаправляет запросы на страницу Swagger-ui
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/");
        registry.addRedirectViewController("/swagger-ui.html", "/swagger-ui/");
        registry.addRedirectViewController("/v3/api-docs", "/v3/api-docs/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PATCH", "DELETE");
    }
}