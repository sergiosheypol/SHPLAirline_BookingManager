package com.shpl.bookingmanager.mapper;

import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.dto.flight.FlightDto;
import com.shpl.bookingmanager.entity.BookingDetails;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponseDto bookingToBookingResponseDto(BookingDetails bookingDetails, FlightDto flightDto,
                                                          String userId) {
        return BookingResponseDto.builder()
                .price(bookingDetails.getPrice())
                .pnr(bookingDetails.getPnr())
                .flightDto(flightDto)
                .dateOfBooking(bookingDetails.getDateOfBooking())
                .userId(userId)
                .build();
    }
}
