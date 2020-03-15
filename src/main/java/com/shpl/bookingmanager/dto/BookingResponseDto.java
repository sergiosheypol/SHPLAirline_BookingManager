package com.shpl.bookingmanager.dto;

import com.shpl.bookingmanager.dto.flight.FlightDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {

    private String userId;
    private String pnr;
    private String price;
    private String dateOfBooking;
    private FlightDto flightDto;
}
