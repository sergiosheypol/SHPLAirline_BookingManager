package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.config.FlightEndpointsProperties;
import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.dto.flight.FlightBookingResultDto;
import com.shpl.bookingmanager.dto.flight.FlightDto;
import com.shpl.bookingmanager.dto.flight.FlightExistsDto;
import com.shpl.bookingmanager.entity.Booking;
import com.shpl.bookingmanager.entity.BookingDetails;
import com.shpl.bookingmanager.mapper.BookingMapper;
import com.shpl.bookingmanager.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingManagerService {

    private final WebClient webClient;

    private final BookingMapper bookingMapper;

    private final FlightEndpointsProperties flightEndpointsProperties;

    private final BookingRepository bookingRepository;

    public Mono<BookingResponseDto> saveNewBooking(BookingPushDto bookingPushDto) {

        return checkIfBookingIsFeasible(bookingPushDto)
                .filter(bool -> bool)
                .flatMap(__ -> bookNewSeat(bookingPushDto.getFlightDto().getId()))
                .zipWith(bookingRepository.findById(bookingPushDto.getUserId()))
                .map(zip -> updateBookingDetails(zip.getT2(), bookingPushDto, zip.getT1()))
                .flatMap(bookingRepository::save)
                .map(booking -> bookingMapper.bookingToBookingResponseDto(
                        booking.getBookings().get(bookingPushDto.getFlightDto().getId()),
                        bookingPushDto.getFlightDto(),
                        bookingPushDto.getUserId()));
    }

    private Mono<Boolean> checkIfBookingIsFeasible(BookingPushDto bookingPushDto) {
        return checkIfFlightAlreadyExists(bookingPushDto.getFlightDto().getId())
                .filter(isFlightAvailable -> !isFlightAvailable)
                .flatMap(__ -> pushFlight(bookingPushDto.getFlightDto()))
                .map(flightDto -> Optional.ofNullable(flightDto.getId()).isPresent());
    }

    private Mono<Boolean> checkIfFlightAlreadyExists(String flightId) {
        return webClient.method(HttpMethod.GET)
                .uri(flightEndpointsProperties.getIsFlightAvailable(), flightId)
                .retrieve()
                .bodyToMono(FlightExistsDto.class)
                .map(FlightExistsDto::isFlightAvailable);
    }

    private Mono<FlightDto> pushFlight(FlightDto flightDto) {
        return webClient.method(HttpMethod.POST)
                .uri(flightEndpointsProperties.getPushFlight())
                .body(flightDto, FlightDto.class)
                .retrieve()
                .bodyToMono(FlightDto.class);
    }

    private Mono<FlightBookingResultDto> bookNewSeat(String flightId) {
        return webClient.method(HttpMethod.POST)
                .uri(flightEndpointsProperties.getSaveNewBooking(), flightId)
                .retrieve()
                .bodyToMono(FlightBookingResultDto.class);
    }

    //TODO: refactor this method
    private Booking updateBookingDetails(Booking booking, BookingPushDto bookingPushDto,
                                         FlightBookingResultDto flightBookingResultDto) {

        Booking finalBooking = Optional.ofNullable(booking)
                .orElse(Booking.builder()
                        .userId(bookingPushDto.getUserId())
                        .bookings(new HashMap<>())
                        .build());

        BookingDetails bookingDetails = BookingDetails.builder()
                .dateOfBooking(LocalDateTime.now().toString())
                .pnr(flightBookingResultDto.getPnr())
                .price(bookingPushDto.getPrice())
                .build();

        finalBooking.getBookings().put(bookingPushDto.getFlightDto().getId(), bookingDetails);

        return finalBooking;

    }
}

