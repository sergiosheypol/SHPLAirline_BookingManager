package com.shpl.bookingmanager;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.dto.flight.FlightBookingResultDto;
import com.shpl.bookingmanager.dto.flight.FlightBookingStatus;
import com.shpl.bookingmanager.dto.flight.FlightDto;
import com.shpl.bookingmanager.entity.Booking;
import com.shpl.bookingmanager.entity.BookingDetails;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class BookingControllerData {

    public static final String USER_ID = "52563562";
    public static final String DEFAULT_USER_ID = "444444";


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
            .userId(USER_ID)
            .build();

    public static final BookingResponseDto TEST_BOOKING_RESPONSE_DTO = BookingResponseDto.builder()
            .pnr("XCR456")
            .userId("52563562")
            .build();

    public static final FlightDto FULL_FLIGHT =
            FlightDto.builder()
                    .id("FRAAVVBB")
                    .iataCode("FR5676")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .build();

    public static final FlightBookingResultDto FLIGHT_BOOKING_RESULT_DTO =
            FlightBookingResultDto.builder()
                    .flightBookingStatus(FlightBookingStatus.CONFIRMED)
                    .pnr("AERTY6")
                    .build();

    public static final Booking BOOKING =
            Booking.builder()
                    .id(DEFAULT_USER_ID)
                    .bookings(getBookingDetailsMap())
                    .build();


    public static Map<String, BookingDetails> getBookingDetailsMap() {
        Map<String, BookingDetails> bookingDetailsMap = new HashMap<>();
        bookingDetailsMap.put("FlightID",
                getBookingDetails());
        return bookingDetailsMap;
    }

    public static BookingDetails getBookingDetails() {
        return BookingDetails.builder().price("13.2").dateOfBooking("DateOfBooking").pnr("RWRWRW").build();
    }


}
