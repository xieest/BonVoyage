package com.app.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.flightmanagementsystem.service.FlightService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	// Displays all flights on the admin dashboard
	@Autowired
	FlightService flightService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("flightsList", flightService.getAllFlights());
		return "flights";
	}
}
