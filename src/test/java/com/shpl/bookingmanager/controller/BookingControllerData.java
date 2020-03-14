package com.shpl.bookingmanager.controller;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.dto.FlightDto;

import java.time.LocalDateTime;

public final class BookingControllerData {

    public static final FlightDto TEST_FLIGHT_DTO = FlightDto.builder()
            .id("FRAAAAAAA")
            .iataCode("FRAAAA")
            .departureAirport("MAD")
            .arrivalAirport("IBZ")
            .connectingAirport("null")
            .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
            .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
            .build();

    public static final BookingPushDto TEST_BOOKING_PUSH_DTO = BookingPushDto.builder()
            .flightDto(TEST_FLIGHT_DTO)
            .price("160.00")
            .userId("52563562")
            .build();

    public static final BookingResponseDto TEST_BOOKING_RESPONSE_DTO = BookingResponseDto.builder()
            .flightDto(TEST_FLIGHT_DTO)
            .dateOfBooking(LocalDateTime.parse("2020-02-27T07:10:00"))
            .pnr("XCR456")
            .price("160.00")
            .userId("52563562")
            .build();
}
