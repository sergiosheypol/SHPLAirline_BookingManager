package com.shpl.bookingmanager.facade;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.entity.Booking;
import com.shpl.bookingmanager.mapper.BookingMapper;
import com.shpl.bookingmanager.service.BookingCrudService;
import com.shpl.bookingmanager.service.BookingOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingFacade {

    private final BookingOrchestratorService bookingOrchestratorService;

    private final BookingMapper bookingMapper;

    private final BookingCrudService bookingCrudService;

    public Mono<BookingResponseDto> saveNewBooking(BookingPushDto bookingPushDto) {

        return bookingOrchestratorService.saveNewBooking(bookingPushDto)
                .map(booking -> bookingMapper.bookingToBookingResponseDto(
                        booking.getBookings().get(bookingPushDto.getFlightDto().getId()),
                        bookingPushDto.getFlightDto(),
                        bookingPushDto.getUserId()))
                .defaultIfEmpty(BookingResponseDto.builder().build());
    }

    public Mono<Booking> getBookingsFromUser(String userId) {
        return bookingCrudService.findBooking(userId);
    }

    public Mono<Booking> deleteBooking(String userId, String flightId) {
        return bookingCrudService.deleteBooking(userId, flightId);
    }
}
