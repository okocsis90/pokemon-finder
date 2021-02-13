package com.alea.pokemonfinder.configuration;

import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.impl.client.HttpClientBuilder;

@Configuration
public class BaseConfiguration {

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }
}
