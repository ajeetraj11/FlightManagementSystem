package com.capge.casestudy.bookingservice;

import com.capge.casestudy.bookingservice.controller.AdminBookingController;
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

@WebMvcTest(AdminBookingController.class)
public class AdminBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUpdateBooking() throws Exception {
        Booking booking = new Booking();
        booking.setFlightNumber("AMI-62");

        Mockito.when(bookingService.updateBooking(Mockito.anyLong(), Mockito.any(Booking.class))).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AMI-62"));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Mockito.doNothing().when(bookingService).deleteBooking(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/bookings/deleteBookingById/1"))
                .andExpect(status().isOk());
    }
}