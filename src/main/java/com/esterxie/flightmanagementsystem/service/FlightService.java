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
	 * This function is getting used to delete the flight from the database
	 *
	 * @param flightNumber
	 */
	public void deleteFlight(String flightNumber) {
		Optional<Flight> flight = flightRepository.findById(flightNumber);
		if (flight.isPresent()) {
			// This line is just breaking the relationship between users and flight
			// Because we can't delete any row if that is somewhere a foreign key.

			// This is because in bridge table we have to specify both keys or parent
			// foreign key.
			// As we are using child key so that's why first have to switch the mode.
			// Else it will not allow it
			flightRepository.setSafeModeOFF();

			flightRepository.deleteFlightByFlight_number(flightNumber);

			// Here we are just turning back that on
			flightRepository.setSafeModeON();
			// In this line after deleting the relationship then we will delete it from
			// database.
			flightRepository.delete(flight.get());
		} else {
			throw new RuntimeException("There is no flight against this ID: " + flightNumber);
		}
	}

	/**
	 * This function is getting used to fetch specific flights that are going to or
	 * going from that city.
	 *
	 * @param keyword
	 * @return
	 */
	public List<Flight> getFlightsByCity(String keyword) {
		return flightRepository
				.findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(keyword, keyword);
	}

	/**
	 * This function is getting used to book a flight for a customer. When the user
	 * will click on Book button then that api in controller will be calling this
	 * button. In this function I am checking who is the current user who is asked
	 * for booking. I'm checking if he already have booked that flight before then
	 * am not booking for it again and just returning. else I am adding that flight
	 * in his booking list.
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
