package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.esterxie.flightmanagementsystem.service.RestCallService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class FlightRestApi {

	@Autowired
	RestCallService restCallService;

	/**
	 * Used to fetch the new data from the 3rd party api Aviation Stacks.
	 *
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/populateNewFlights")
	public String calling3rdPartyApi() throws JsonProcessingException {
		restCallService.callingFlightApi();
		return "redirect:/flight/list";
	}
}
