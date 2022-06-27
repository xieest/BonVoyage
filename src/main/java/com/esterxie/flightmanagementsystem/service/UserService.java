package com.esterxie.flightmanagementsystem.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.model.User;
import com.esterxie.flightmanagementsystem.repository.FlightRepository;
import com.esterxie.flightmanagementsystem.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final FlightRepository flightRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository userRepository, FlightRepository flightRepository) {
		this.userRepository = userRepository;
		this.flightRepository = flightRepository;
	}

	public User saveUser(User user) {
		log.info("Trying to add the user in the database: {}", user);
		try {
			user.setRole("ROLE_USER");
			user.setPassword(encoder.encode(user.getPassword()));
			return userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<User> getAllUsers() {
		return userRepository.getAllUsersFromDatabase();
	}

	/**
	 * In this function we are finding the user from the database against its email
	 *
	 * @param email
	 * @return
	 */
	public Optional<User> findUserByEmail(String email) {
		try {
			return userRepository.findUserByEmail(email);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Flight> getSpecificUserFlights(String email) {

		Optional<User> user = userRepository.findUserByEmail(email);
		if (user.isPresent()) {
			return user.get().getFlights();
		} else {
			throw new RuntimeException("User doesn't exists: ");
		}
	}

	/**
	 * This function is getting used to delete a specific flight from a specific
	 * users list first we are finding whose user is that, then finding that flight
	 * and just removing that from his list
	 *
	 * @param flightNumber
	 * @param email
	 */
	public void deleteBookedFlight(String flightNumber, String email) {
		Optional<User> user = userRepository.findUserByEmail(email);
		if (user.isPresent()) {

			Optional<Flight> flight = flightRepository.findById(flightNumber);
			if (flight.isPresent()) {
				user.get().getFlights().remove(flight.get());
				userRepository.save(user.get());
			} else {
				throw new RuntimeException("There is no fight in the current user list");
			}
		} else {
			throw new RuntimeException("User doesn't exists: ");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(email);
		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
					user.get().getPassword(), getAuthority(user.get()));
		} else {
			throw new RuntimeException("User doesn't exists against email: " + email);
		}
	}

	/**
	 * This function is providing the role of the user that we have in the database.
	 *
	 * @param user
	 * @return
	 */
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return authorities;
	}
}
