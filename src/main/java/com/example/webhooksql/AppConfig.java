package com.example.webhooksql;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AppConfig {


@Value("${app.generate-url:https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA}")
private String generateUrl;


@Bean
WebClient webClient(WebClient.Builder builder) {
return builder.baseUrl(generateUrl).build();
}
}