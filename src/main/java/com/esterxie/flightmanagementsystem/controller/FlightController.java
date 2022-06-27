package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.esterxie.flightmanagementsystem.service.FlightService;

@Controller
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	FlightService flightService;

	// Allows admins the authority to delete flights
	@GetMapping("/delete/{id}")
	public String deleteFlight(@PathVariable(name = "id") String flightNumber) {
		flightService.deleteFlight(flightNumber);
		return "redirect:/admin/dashboard";
	}

	// Search for flights by keywords
	@GetMapping("/search")
	public String searchFlightCity(@RequestParam(name = "keyword") String keyword, Model model) {
		model.addAttribute("flightsList", flightService.getFlightsByCity(keyword));
		return "flights";
	}
}
