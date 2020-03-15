package com.shpl.bookingmanager.controller;

import com.shpl.bookingmanager.dto.BookingPushDto;
import com.shpl.bookingmanager.dto.BookingResponseDto;
import com.shpl.bookingmanager.service.BookingManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingManagerService bookingManagerService;

    @PostMapping("/saveBooking")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Mono<BookingResponseDto> saveNewBooking(@Valid @RequestBody BookingPushDto bookingPushDto) {
        return bookingManagerService.saveNewBooking(bookingPushDto);
    }

}
