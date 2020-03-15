package com.shpl.bookingmanager.service;

import com.shpl.bookingmanager.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingCrudService {
    private final BookingRepository bookingRepository;
}
