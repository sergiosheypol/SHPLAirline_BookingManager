package com.shpl.bookingmanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BookingManagerConfig {

    private final FlightEndpointsProperties flightEndpointsProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.create(flightEndpointsProperties.getBase());
    }
}
