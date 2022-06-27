package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esterxie.flightmanagementsystem.service.RestCallService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class FlightRestApi {

	@Autowired
	RestCallService restCallService;

	// Uses Aviation Flights API to bring in flight data
	@GetMapping("/populateNewFlights")
	public ResponseEntity<?> calling3rdPartyApi() throws JsonProcessingException {
		restCallService.callingFlightApi();
		return new ResponseEntity<>("Flights have been successfully updated" + " in the database for the upcoming date",
				HttpStatus.OK);
	}
}
