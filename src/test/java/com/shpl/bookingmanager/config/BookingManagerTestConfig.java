package com.shpl.bookingmanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
@RequiredArgsConstructor
public class BookingManagerTestConfig {

    private final FlightEndpointsProperties flightEndpointsProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.create(flightEndpointsProperties.getBase() + ":" + flightEndpointsProperties.getPort());
    }
}
