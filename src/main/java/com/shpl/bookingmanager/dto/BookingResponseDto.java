package com.shpl.bookingmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {

    private String userId;
    private String pnr;
    private String price;
    private LocalDateTime dateOfBooking;
    private FlightDto flightDto;
}
