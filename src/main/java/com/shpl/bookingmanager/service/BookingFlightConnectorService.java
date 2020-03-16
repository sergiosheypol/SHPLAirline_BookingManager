package com.shpl.bookingmanager.service;


import com.shpl.bookingmanager.config.FlightEndpointsProperties;
import com.shpl.bookingmanager.dto.flight.FlightBookingResultDto;
import com.shpl.bookingmanager.dto.flight.FlightDto;
import com.shpl.bookingmanager.dto.flight.FlightExistsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingFlightConnectorService {

    private final FlightEndpointsProperties flightEndpointsProperties;

    private final WebClient webClient;

    public Mono<Boolean> checkIfFlightAlreadyExists(String flightId) {
        return webClient.method(HttpMethod.GET)
                .uri(flightEndpointsProperties.getIsFlightAvailable(), flightId)
                .retrieve()
                .bodyToMono(FlightExistsDto.class)
                .map(FlightExistsDto::isFlightAvailable)
                .defaultIfEmpty(false);
    }

    public Mono<FlightDto> pushFlight(FlightDto flightDto) {
        return webClient.method(HttpMethod.POST)
                .uri(flightEndpointsProperties.getPushFlight())
                .body(Mono.just(flightDto), FlightDto.class)
                .retrieve()
                .bodyToMono(FlightDto.class);
    }

    public Mono<FlightBookingResultDto> bookNewSeat(String flightId) {
        return webClient.method(HttpMethod.POST)
                .uri(flightEndpointsProperties.getSaveNewBooking(), flightId)
                .retrieve()
                .bodyToMono(FlightBookingResultDto.class);
    }
}
