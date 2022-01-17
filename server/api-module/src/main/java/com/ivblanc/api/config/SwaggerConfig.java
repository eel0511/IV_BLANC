/**
 * Created by DominikH on 24.04.2017.
 */
package com.ivblanc.api.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String version;
    private String title;

    @Value("${swagger.host}")
    private String host;

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "SSAFY API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ivblanc.api.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo(title, version));

    }


    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "SSAFY API",
                version,
                "www.example.com",
                new Contact("Contact Me", "www.example.com", "test@example.com"),
                "Licenses",
                "www.example.com",
                new ArrayList<>());
    }
}
