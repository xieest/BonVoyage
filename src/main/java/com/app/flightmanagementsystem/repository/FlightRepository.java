package com.app.flightmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.flightmanagementsystem.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
	List<Flight> findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(
			String arrivalAirport, String departureAirport);

	// Custom query to find flights by departure
	@Query(value = "SELECT * FROM flights order by departure_estimated", nativeQuery = true)
	List<Flight> getAllByFlightsOrderByDepartureDate();
}
