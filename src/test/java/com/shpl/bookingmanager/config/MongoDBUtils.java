package com.shpl.bookingmanager.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.shpl.bookingmanager.BookingControllerData;
import com.shpl.bookingmanager.mapper.BookingMapper;
import com.shpl.bookingmanager.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBUtils {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoDBProperties mongoDBProperties;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingMapper bookingMapper;

    public void createCollection() {
        mongoClient
                .getDatabase(mongoDBProperties.getDatabaseName())
                .createCollection(mongoDBProperties.getFlightsCollectionName());
    }

    public void createTestBooking() {
        bookingRepository.save(BookingControllerData.BOOKING).block();
    }


}
