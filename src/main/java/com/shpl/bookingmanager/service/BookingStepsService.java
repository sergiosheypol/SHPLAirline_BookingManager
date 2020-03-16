package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.flight.FlightBookingResultDto;
import com.shpl.bookingmanager.dto.flight.FlightDto;
import com.shpl.bookingmanager.entity.Booking;
import com.shpl.bookingmanager.entity.BookingDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingStepsService {

    private final BookingFlightConnectorService bookingFlightConnectorService;

    public Mono<Boolean> isFlightAlreadyRegistered(BookingPushDto bookingPushDto) {
        return bookingFlightConnectorService.checkIfFlightAlreadyExists(bookingPushDto.getFlightDto().getId())
                .filter(isFlightAvailable -> isFlightAvailable)
                .defaultIfEmpty(false);
    }

    public Mono<Boolean> pushFlight(FlightDto flightDto) {
        return bookingFlightConnectorService.pushFlight(flightDto)
                .map(flightDtoMap -> !flightDtoMap.getId().equals(""))
                .defaultIfEmpty(false);
    }

    //TODO: refactor this method
    public Booking updateBookingDetails(Booking booking, BookingPushDto bookingPushDto,
                                        FlightBookingResultDto flightBookingResultDto) {

        Booking finalBooking = booking
                .withId(Optional.ofNullable(booking.getId()).orElse(bookingPushDto.getUserId()))
                .withBookings(Optional.ofNullable(booking.getBookings()).orElse(new HashMap<>()));


        BookingDetails bookingDetails = BookingDetails.builder()
                .dateOfBooking(LocalDateTime.now().toString())
                .pnr(flightBookingResultDto.getPnr())
                .price(bookingPushDto.getPrice())
                .build();

        finalBooking.getBookings().put(bookingPushDto.getFlightDto().getId(), bookingDetails);

        return finalBooking;

    }
}

