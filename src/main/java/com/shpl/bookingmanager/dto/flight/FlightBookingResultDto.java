package com.shpl.bookingmanager.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingResultDto {
    private FlightBookingStatus flightBookingStatus;
    private String pnr;
}
