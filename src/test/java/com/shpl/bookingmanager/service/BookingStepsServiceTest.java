package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.BookingControllerData;
import com.shpl.bookingmanager.entity.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BookingStepsServiceTest {

    @InjectMocks
    private BookingStepsService bookingStepsService;

    @Mock
    private BookingFlightConnectorService bookingFlightConnectorService;

    @Test
    public void checkIfBookingIsFeasibleWhenFlightDoesntExist() {
        when(bookingFlightConnectorService.checkIfFlightAlreadyExists(anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(bookingStepsService.isFlightAlreadyRegistered(BookingControllerData.TEST_BOOKING_PUSH_DTO))
                .assertNext(bool -> {
                    assertThat(bool).isFalse();
                })
                .verifyComplete();
    }

    @Test
    public void checkIfBookingIsFeasibleWhenFlightExists() {
        when(bookingFlightConnectorService.checkIfFlightAlreadyExists(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(bookingStepsService.isFlightAlreadyRegistered(BookingControllerData.TEST_BOOKING_PUSH_DTO))
                .assertNext(bool -> {
                    assertThat(bool).isTrue();
                })
                .verifyComplete();
    }

    @Test
    public void shouldPushFlightIfDoesntExist() {
        when(bookingFlightConnectorService.pushFlight(eq(BookingControllerData.TEST_FLIGHT_DTO)))
                .thenReturn(Mono.just(BookingControllerData.TEST_FLIGHT_DTO));

        StepVerifier.create(bookingStepsService.pushFlight(BookingControllerData.TEST_FLIGHT_DTO))
                .assertNext(bool -> {
                    assertThat(bool).isTrue();
                })
                .verifyComplete();
    }


    @Test
    public void shouldUpdateBooking() {
        Booking booking = bookingStepsService.updateBookingDetails(
                BookingControllerData.BOOKING,
                BookingControllerData.TEST_BOOKING_PUSH_DTO,
                BookingControllerData.FLIGHT_BOOKING_RESULT_DTO);

        assertThat(booking.getBookings().get("FlightID")).isEqualTo(BookingControllerData.getBookingDetails());


    }

    @Test
    public void shouldUpdateEmptyBooking() {
        Booking booking = bookingStepsService.updateBookingDetails(
                null,
                BookingControllerData.TEST_BOOKING_PUSH_DTO,
                BookingControllerData.FLIGHT_BOOKING_RESULT_DTO);

        assertThat(booking.getBookings().get("FRAAAAAAA").getPnr()).isEqualTo(BookingControllerData.FLIGHT_BOOKING_RESULT_DTO.getPnr());
        assertThat(booking.getBookings().get("FlightID")).isNull();

    }
}
