package com.shpl.bookingmanager.mapper;

import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.entity.BookingDetails;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponseDto bookingToBookingResponseDto(BookingDetails bookingDetails, String userId) {
        return BookingResponseDto.builder()
                .pnr(bookingDetails.getPnr())
                .userId(userId)
                .build();
    }
}
