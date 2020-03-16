package com.shpl.bookingmanager.controller;


import com.shpl.bookingmanager.BookingControllerData;
import com.shpl.bookingmanager.facade.BookingFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingFacade bookingFacade;

    @Test
    public void shouldSaveNewBooking() {

        when(bookingFacade.saveNewBooking(eq(BookingControllerData.TEST_BOOKING_PUSH_DTO)))
                .thenReturn(Mono.just(BookingControllerData.TEST_BOOKING_RESPONSE_DTO));

        StepVerifier.create(bookingController.saveNewBooking(BookingControllerData.TEST_BOOKING_PUSH_DTO))
                .assertNext(bookingResponseDto -> {
                    assertThat(bookingResponseDto.getPnr()).isEqualTo(BookingControllerData.TEST_BOOKING_RESPONSE_DTO.getPnr());
                })
                .verifyComplete();

    }


}
