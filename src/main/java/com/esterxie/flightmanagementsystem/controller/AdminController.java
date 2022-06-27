package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esterxie.flightmanagementsystem.service.FlightService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	FlightService flightService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("flightsList", flightService.getAllFlightsSorted());
		return "flights";
	}
}
