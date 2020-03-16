package com.shpl.bookingmanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Data
@Component
@ConstructorBinding
@ConfigurationProperties(prefix = "flights")
public class FlightEndpointsProperties {
    private String base;
    private String port;
    private String saveNewBooking;
    private String isFlightAvailable;
    private String pushFlight;
}
