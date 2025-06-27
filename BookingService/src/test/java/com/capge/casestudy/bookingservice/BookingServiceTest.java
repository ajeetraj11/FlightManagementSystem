package com.capge.casestudy.bookingservice;

import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.exception.SeatUnavailableException;
import com.capge.casestudy.bookingservice.repository.BookingRepository;
import com.capge.casestudy.bookingservice.service.BookingServiceImpl;
import com.capge.casestudy.bookingservice.service.fiegn.FlightFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightFeignClient flightFeignClient;

    @Test
    public void testCreateBooking_Success() {
        Booking booking = new Booking();
        booking.setFlightNumber("AMI-62");
        booking.setNumberOfSeats(2);
        booking.setTotalAmount(5000.0);

        Mockito.when(flightFeignClient.getAvailableSeats("AMI-62")).thenReturn(5);
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    public void testCreateBooking_InsufficientSeats() {
        Booking booking = new Booking();
        booking.setFlightNumber("AMI-62");
        booking.setNumberOfSeats(10);

        Mockito.when(flightFeignClient.getAvailableSeats("AMI-62")).thenReturn(5);

        assertThrows(SeatUnavailableException.class, () -> bookingService.createBooking(booking));
    }
}