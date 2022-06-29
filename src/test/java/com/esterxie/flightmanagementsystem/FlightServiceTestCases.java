package com.esterxie.flightmanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.repository.FlightRepository;
import com.esterxie.flightmanagementsystem.service.FlightService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class FlightServiceTestCases {

	@MockBean
	private FlightRepository flightRepository;

	@Autowired
	private FlightService flightService;

	/**
	 * Tests our getAllFlightsSorted function. Checks whether our repository returns
	 * the correct response or not.
	 */
	@Test
	public void getAllFlightsSortedTest() {
		Flight flight = new Flight();
		flight.setFlight_number("TestingTheFlight");
		flight.setArrivalAirport("Karachi Airport");
		flight.setDepartureAirport("Islamabad Airport");
		flight.setAirline_name("PIA");

		List<Flight> flightsList = new ArrayList<>();
		flightsList.add(flight);
		when(flightRepository.getAllByFlightsOrderByDepartureDate()).thenReturn(flightsList);
		assertEquals(flightsList, flightService.getAllFlightsSorted());
	}

	/**
	 * Tests our getAllFlightsWithoutSort function. Checks whether our repository
	 * returns the correct response or not.
	 */
	@Test
	public void getAllFlightsWithoutSortTest() {
		Flight flight = new Flight();
		flight.setFlight_number("TestingTheFlight");
		flight.setArrivalAirport("Karachi Airport");
		flight.setDepartureAirport("Islamabad Airport");
		flight.setAirline_name("PIA");

		List<Flight> flightsList = new ArrayList<>();
		flightsList.add(flight);
		when(flightRepository.getAllByFlights()).thenReturn(flightsList);
		assertEquals(flightsList, flightService.getAllFlightsWithoutSort());
	}

	/**
	 * Tests getFlightsByCity method. Here we are checking the output related to a
	 * specific city In this we are checking whether our repository will return our
	 * expected result or not.
	 */
	@Test
	public void getFlightsByCityTest() {
		Flight flight = new Flight();
		flight.setFlight_number("TestingTheFlight");
		flight.setArrivalAirport("Karachi Airport");
		flight.setDepartureAirport("Islamabad Airport");
		flight.setAirline_name("PIA");

		List<Flight> flightsList = new ArrayList<>();
		flightsList.add(flight);
		when(flightRepository.findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(
				"Karachi", "Karachi")).thenReturn(flightsList);
		assertEquals(flightsList, flightService.getFlightsByCity("Karachi"));
	}
}
