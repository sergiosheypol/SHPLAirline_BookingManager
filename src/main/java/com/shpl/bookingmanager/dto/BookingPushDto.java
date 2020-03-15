package com.shpl.bookingmanager.dto;

import com.shpl.bookingmanager.dto.flight.FlightDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingPushDto {

    @NotBlank(message = "ID is required")
    private String userId;

    @NotBlank(message = "Price is required")
    private String price;

    @NotNull(message = "Flight details are required")
    private FlightDto flightDto;
}
