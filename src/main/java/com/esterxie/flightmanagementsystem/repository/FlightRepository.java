package com.esterxie.flightmanagementsystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esterxie.flightmanagementsystem.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
	List<Flight> findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(
			String arrivalAirport, String departureAirport);

	@Query(value = "SELECT * FROM flights", nativeQuery = true)
	List<Flight> getAllByFlights();

	@Query(value = "SELECT * FROM flights order by departure_estimated desc", nativeQuery = true)
	List<Flight> getAllByFlightsOrderByDepartureDate();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM users_flights where flights_flight_number=?1", nativeQuery = true)
	void deleteFlightByFlight_number(String flightNumber);

	@Modifying
	@Transactional
	@Query(value = "SET SQL_SAFE_UPDATES = 0;", nativeQuery = true)
	void setSafeModeOFF();

	@Modifying
	@Transactional
	@Query(value = "SET SQL_SAFE_UPDATES = 1;", nativeQuery = true)
	void setSafeModeON();

}
