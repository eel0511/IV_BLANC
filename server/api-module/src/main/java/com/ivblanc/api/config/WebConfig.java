package com.ivblanc.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// registry.addMapping("/**")
		// 	.allowedOrigins("*")
		// 	.allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.PATCH.name(),HttpMethod.DELETE.name(),HttpMethod.HEAD.name())
		// 	.allowCredentials(false).maxAge(3600);

		registry.addMapping("/**")
			.allowedOrigins("http://i6d104.p.ssafy.io")
			.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "HEAD")
			.allowedHeaders("*")
			.exposedHeaders("Set-Cookie")
			.allowCredentials(true);
	}
}
