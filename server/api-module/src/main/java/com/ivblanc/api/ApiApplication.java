package com.ivblanc.api;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@ComponentScan({"com.ivblanc.core"})
@ComponentScan({"com.ivblanc.api"})
@EntityScan("com.ivblanc.core")
@EnableJpaRepositories("com.ivblanc.core")
public class ApiApplication {
    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println(new Date());
    }
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
