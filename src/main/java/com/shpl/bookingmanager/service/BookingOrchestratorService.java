package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.flight.FlightBookingStatus;
import com.shpl.bookingmanager.entity.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingOrchestratorService {
    private final BookingStepsService bookingStepsService;

    private final BookingFlightConnectorService bookingFlightConnectorService;

    private final BookingCrudService bookingCrudService;

    public Mono<Booking> saveNewBooking(BookingPushDto bookingPushDto) {

        log.info("New booking received: " + bookingPushDto.getUserId());

        return bookingStepsService.isFlightAlreadyRegistered(bookingPushDto)
                .filter(bool -> bool)
                .switchIfEmpty(bookingStepsService.pushFlight(bookingPushDto.getFlightDto()))
                .filter(bool -> bool)
                // Start the booking if all asserts are true
                .then(bookingFlightConnectorService.bookNewSeat(bookingPushDto.getFlightDto().getId()))
                .filter(flightBookingResultDto -> flightBookingResultDto.getFlightBookingStatus().equals(FlightBookingStatus.CONFIRMED))
                .zipWith(bookingCrudService.findBooking(bookingPushDto.getUserId()))
                .map(zip -> bookingStepsService.updateBookingDetails(zip.getT2(), bookingPushDto, zip.getT1()))
                .flatMap(bookingCrudService::saveBooking)
                .defaultIfEmpty(Booking.builder().build());
    }
}
