package com.esterxie.flightmanagementsystem;

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

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTestCases {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
