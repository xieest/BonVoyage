package com.esterxie.flightmanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.model.User;
import com.esterxie.flightmanagementsystem.repository.UserRepository;
import com.esterxie.flightmanagementsystem.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTestCases {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	/**
	 * Tests the saveUser method in the service class and in our repository. Then
	 * checks whether it will return the expected result or not. After saving the
	 * user in the database, it will return our expected returned user.
	 */
	@Test
	public void saveUserTest() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setEmail("test@gmail.com");
		user.setRepeatedEmail("test@gmail.com");
		user.setPassword("password");
		user.setRepeatedPassword("password");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
	}

	/**
	 * Tests our findUserByEmail method in the service class and in our repository.
	 * Then checks whether it will return the expected result or not that it should
	 * be. It should return the list of users.
	 */
	@Test
	public void getAllUsersTest() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setEmail("test@gmail.com");
		user.setRepeatedEmail("test@gmail.com");
		user.setPassword("password");
		user.setRepeatedPassword("password");
		when(userRepository.getAllUsersFromDatabase()).thenReturn(Collections.singletonList(user));
		assertEquals(Collections.singletonList(user), userService.getAllUsers());
	}

	/**
	 * Tests the findUserByEmail method in the service class and in our repository.
	 */
	@Test
	public void findUserByEmailTest() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setEmail("test@gmail.com");
		user.setRepeatedEmail("test@gmail.com");
		user.setPassword("password");
		user.setRepeatedPassword("password");
		when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(user));
		assertEquals(Optional.of(user), userService.findUserByEmail("test@gmail.com"));
	}

	/**
	 * Tests the getSpecificUserFlights method in the service class and in our
	 * repository.
	 */
	@Test
	public void getSpecificUserFlightsTest() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setEmail("test@gmail.com");
		user.setRepeatedEmail("test@gmail.com");
		user.setPassword("password");
		user.setRepeatedPassword("password");

		Flight flight = new Flight();
		flight.setFlight_number("TestingTheFlight");
		flight.setArrivalAirport("Karachi Airport");
		flight.setDepartureAirport("Islamabad Airport");
		flight.setAirline_name("PIA");

		user.getFlights().add(flight);

		when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(user));
		assertEquals(user.getFlights(), userService.getSpecificUserFlights("test@gmail.com"));
	}

}
