package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.entity.Booking;
import com.shpl.bookingmanager.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingCrudService {
    private final BookingRepository bookingRepository;

    public Mono<Booking> findBooking(String userId) {
        return bookingRepository.findById(userId).defaultIfEmpty(Booking.builder().build());
    }

    public Mono<Booking> saveBooking(Booking booking) {
        return bookingRepository.save(booking).defaultIfEmpty(Booking.builder().build());
    }

    public Mono<Booking> deleteBooking(String userId, String flightId) {
        return bookingRepository.findById(userId)
                .map(booking -> {
                    booking.getBookings().remove(flightId);
                    return booking;
                })
                .flatMap(bookingRepository::save)
                .defaultIfEmpty(Booking.builder().build());
    }
}
