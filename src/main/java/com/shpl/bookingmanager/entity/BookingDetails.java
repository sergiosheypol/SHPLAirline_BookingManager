package com.shpl.bookingmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {

    private String pnr;

    private String flightId;

    private String price;

    private String dateOfBooking;
}
