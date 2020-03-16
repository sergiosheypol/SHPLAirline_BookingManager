package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.BookingApplication;
import com.shpl.bookingmanager.BookingControllerData;
import com.shpl.bookingmanager.config.FlightEndpointsProperties;
import com.shpl.bookingmanager.dto.flight.FlightBookingStatus;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        classes = {BookingApplication.class, BookingFlightConnectorService.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BookingFlightConnectorServiceTest {


    private final static String FLIGHT_ID = "FEEEEE";

    @Autowired
    private FlightEndpointsProperties flightEndpointsProperties;

    @Autowired
    private BookingFlightConnectorService bookingFlightConnectorService;


    @Test
    public void shouldReturnFalseFlight() {

        StepVerifier.create(bookingFlightConnectorService.checkIfFlightAlreadyExists(FLIGHT_ID))
                .assertNext(bool -> {
                    assertThat(bool).isFalse();
                })
                .verifyComplete();

    }

    @Test
    @Order(2)
    public void shouldReturnTrueFlight() {

        StepVerifier.create(bookingFlightConnectorService.checkIfFlightAlreadyExists(BookingControllerData.FULL_FLIGHT.getId()))
                .assertNext(bool -> {
                    assertThat(bool).isTrue();
                })
                .verifyComplete();

    }

    @Test
    @Order(1)
    public void shouldPushFlightDto() {
        StepVerifier.create(bookingFlightConnectorService.pushFlight(BookingControllerData.FULL_FLIGHT))
                .assertNext(flightDto -> {
                    assertThat(flightDto.getId()).isEqualTo(BookingControllerData.FULL_FLIGHT.getId());
                })
                .verifyComplete();
    }

    @Test

    public void shouldBookNewSeat() {
        StepVerifier.create(bookingFlightConnectorService.bookNewSeat(BookingControllerData.FULL_FLIGHT.getId()))
                .assertNext(bookingResultDto -> {
                    assertThat(bookingResultDto.getFlightBookingStatus()).isEqualTo(FlightBookingStatus.CONFIRMED);
                })
                .verifyComplete();
    }

}
