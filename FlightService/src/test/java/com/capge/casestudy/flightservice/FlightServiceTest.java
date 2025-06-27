package com.capge.casestudy.flightservice;

import com.capge.casestudy.flightservice.entity.Flight;
import com.capge.casestudy.flightservice.entity.RecurrenceType;
import com.capge.casestudy.flightservice.exception.FormateNotAllowedException;
import com.capge.casestudy.flightservice.exception.NoFlightFoundException;
import com.capge.casestudy.flightservice.repository.FlightRepository;
import com.capge.casestudy.flightservice.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightService flightService;

	@Test
	void testGetAllFlights() {
        Flight flight1 = new Flight(
                1L,
                "AI212",
                "Air India",
                "Pune",
                "Delhi",
                "10:00",
                "12:00",
                6500.50,
                180,
                120,
                LocalDate.parse("2025-02-10"),
                LocalDate.parse("2025-02-10"),
                RecurrenceType.DAILY,
                false,
                LocalDate.parse("2025-02-10")
        );
        Flight flight2 = new Flight(
                2L,
                "AI201",
                "Air India",
                "Mumbai",
                "Delhi",
                "10:00",
                "12:00",
                6500.50,
                180,
                120,
                LocalDate.parse("2025-02-10"),
                LocalDate.parse("2025-02-10"),
                RecurrenceType.DAILY,
                false,
                LocalDate.parse("2025-02-10")
        );

		when(flightRepository.findAll()).thenReturn(List.of(flight1, flight2));

		List<Flight> flights = flightService.getAllFlights();

		assertEquals(2, flights.size());
		verify(flightRepository, times(1)).findAll();
	}

	@Test
	void testGetFlightById() {
        Flight flight = new Flight(
                1L,
                "AI202",
                "Air India",
                "Mumbai",
                "Delhi",
                "10:00",
                "12:00",
                6500.50,
                180,
                120,
                LocalDate.parse("2025-02-10"),
                LocalDate.parse("2025-02-10"),
                RecurrenceType.DAILY,
                false,
                LocalDate.parse("2025-02-10")
        );
		when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

		Optional<Flight> result = flightService.getFlightById(1L);

		assertTrue(result.isPresent());
		assertEquals("AI202", result.get().getFlightNumber());
		verify(flightRepository, times(1)).findById(1L);
	}

	@Test
	void testCreateFlight() throws FormateNotAllowedException {
        Flight flight = new Flight(
                1L,
                "AI202",
                "Air India",
                "Mumbai",
                "Delhi",
                "10:00",
                "12:00",
                6500.50,
                180,
                120,
                LocalDate.parse("2025-02-10"),
                LocalDate.parse("2025-02-10"),
                RecurrenceType.DAILY,
                false,
                LocalDate.parse("2025-02-10")
        );
		when(flightRepository.save(flight)).thenReturn(flight);

		Flight savedFlight = flightService.createFlight(flight);

		assertEquals("AI202", savedFlight.getFlightNumber());
		verify(flightRepository, times(1)).save(flight);
	}

	@Test
	void testDeleteFlightSuccess() throws NoFlightFoundException {
        Flight flight = new Flight(
                1L,
                "AI202",
                "Air India",
                "Mumbai",
                "Delhi",
                "10:00",
                "12:00",
                6500.50,
                180,
                120,
                LocalDate.parse("2025-02-10"),
                LocalDate.parse("2025-02-10"),
                RecurrenceType.DAILY,
                false,
                LocalDate.parse("2025-02-10")
        );
		when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

		flightService.deleteFlight(1L);
		verify(flightRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteFlightFailure() {
		when(flightRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoFlightFoundException.class, () -> flightService.deleteFlight(1L));
		verify(flightRepository, never()).deleteById(1L);
	}
}