package com.shpl.bookingmanager.repository;

import com.shpl.bookingmanager.entity.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}
