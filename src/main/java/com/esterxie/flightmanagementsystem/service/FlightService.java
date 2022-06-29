package com.esterxie.flightmanagementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.model.User;
import com.esterxie.flightmanagementsystem.repository.FlightRepository;
import com.esterxie.flightmanagementsystem.repository.UserRepository;

@Service
public class FlightService {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	UserRepository userRepository;

	public List<Flight> getAllFlightsSorted() {
		return flightRepository.getAllByFlightsOrderByDepartureDate();
	}

	public List<Flight> getAllFlightsWithoutSort() {
		return flightRepository.getAllByFlights();
	}

	/**
	 * Used to delete the flight from the database
	 *
	 * @param flightNumber
	 */
	public void deleteFlight(String flightNumber) {
		Optional<Flight> flight = flightRepository.findById(flightNumber);
		if (flight.isPresent()) {

			// To allow us to delete a key
			flightRepository.setSafeModeOFF();

			flightRepository.deleteFlightByFlight_number(flightNumber);

			// Turn back on safe mode so that keys aren't accidentally deleted
			flightRepository.setSafeModeON();

			// Delete the flight from the database.
			flightRepository.delete(flight.get());
		} else {
			throw new RuntimeException("There is no flight against this ID: " + flightNumber);
		}
	}

	/**
	 * Fetch flights going to a specific city
	 *
	 * @param keyword
	 * @return
	 */
	public List<Flight> getFlightsByCity(String keyword) {
		return flightRepository
				.findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(keyword, keyword);
	}

	/**
	 * Used to book a flight for a user. Checks who is the current user. If he
	 * already has that specific flight booked, return the flight. Else add that
	 * flight in his booking list.
	 *
	 * @param flightNumber
	 */
	public void bookFlight(String flightNumber, String email) {
		Optional<Flight> flight = flightRepository.findById(flightNumber);
		if (flight.isPresent()) {

			Optional<User> user = userRepository.findUserByEmail(email);

			if (user.isPresent()) {
				if (user.get().getFlights().contains(flight.get())) {
					return;
				}
				user.get().getFlights().add(flight.get());
				userRepository.save(user.get());
			} else {
				throw new RuntimeException("User doesn't exists: ");
			}

		} else {
			throw new RuntimeException("There is no flight against this ID: " + flightNumber);
		}
	}
}
