package com.shpl.bookingmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Booking {
    private String userId;
    private List<BookingDetails> bookings;

}
