package com.ivblanc.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ivblanc.api.dto.StyleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class styleConfig extends WebMvcConfigurationSupport {

    // 생략

    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StyleConverter(objectMapper()));
    }
}