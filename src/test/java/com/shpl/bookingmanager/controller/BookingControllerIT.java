package com.shpl.bookingmanager.controller;

import com.shpl.bookingmanager.BookingControllerData;
import com.shpl.bookingmanager.config.MongoDBUtils;
import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.entity.Booking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class BookingControllerIT {

    private final static String SAVE_BOOKING_ENDPOINT = "/booking/saveBooking";
    private final static String GET_BOOKINGS_ENDPOINT = "/booking/getBookings/{userId}";
    private final static String DELETE_BOOKINGS_ENDPOINT = "/booking/deleteBooking/{userId}/{flightId}";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MongoDBUtils mongoDBUtils;


    @Before
    public void setUp() {
        mongoDBUtils.createCollection();
        mongoDBUtils.createTestBooking();
    }

    @Test
    public void shouldSaveNewBooking() {
        BookingResponseDto responseBody = this.webTestClient
                .post()
                .uri(SAVE_BOOKING_ENDPOINT)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(BookingControllerData.TEST_BOOKING_PUSH_DTO), BookingPushDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(BookingResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getFlightDto()).isEqualTo(BookingControllerData.TEST_FLIGHT_DTO);
        assertThat(responseBody.getPnr()).isNotNull();

    }

    @Test
    public void shouldGetBookings() {
        Booking res = this.webTestClient
                .get()
                .uri(GET_BOOKINGS_ENDPOINT, BookingControllerData.DEFAULT_USER_ID)
                .exchange()
                .expectBody(Booking.class)
                .returnResult()
                .getResponseBody();

        assertThat(res.getBookings().get("FlightID")).isEqualTo(BookingControllerData.getBookingDetails());
    }

    @Test
    public void shouldDeleteBooking() {
        Booking res = this.webTestClient
                .post()
                .uri(DELETE_BOOKINGS_ENDPOINT, BookingControllerData.DEFAULT_USER_ID, "FlightID")
                .exchange()
                .expectBody(Booking.class)
                .returnResult()
                .getResponseBody();

        assertThat(res.getBookings().get("FlightID")).isNull();
    }


}
