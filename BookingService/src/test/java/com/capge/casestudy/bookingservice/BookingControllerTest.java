package com.capge.casestudy.bookingservice;

import com.capge.casestudy.bookingservice.controller.BookingController;
import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateBooking() throws Exception {
        Booking booking = new Booking();
        booking.setFlightNumber("AMI-62");
        booking.setNumberOfSeats(2);

        Mockito.when(bookingService.createBooking(Mockito.any(Booking.class))).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AMI-62"));
    }

    @Test
    public void testGetBookingByPNR() throws Exception {
        Booking booking = new Booking();
        booking.setPnr("ABC123");

        Mockito.when(bookingService.getBookingByPNR("ABC123")).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/getBookingByPnr/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnr").value("ABC123"));
    }
}