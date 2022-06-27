package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.esterxie.flightmanagementsystem.service.FlightService;
import com.esterxie.flightmanagementsystem.service.UserService;

@Controller
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	FlightService flightService;

	@Autowired
	UserService userService;

	@GetMapping("/list")
	public String getAllFlights(Model model) {
		model.addAttribute("flightsList", flightService.getAllFlightsSorted());
		return "flights";
	}

	@GetMapping("/delete/{id}")
	public String deleteFlight(@PathVariable(name = "id") String flightNumber) {
		flightService.deleteFlight(flightNumber);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/search")
	public String searchFlightCity(@RequestParam(name = "keyword") String keyword, Model model) {
		model.addAttribute("flightsList", flightService.getFlightsByCity(keyword));
		return "flights";
	}

	@GetMapping("/book/{id}")
	public String bookFlight(@PathVariable(name = "id") String flightNumber, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		flightService.bookFlight(flightNumber, email);
		model.addAttribute("flightsList", userService.getSpecificUserFlights(email));
		return "userflights";
	}
}
