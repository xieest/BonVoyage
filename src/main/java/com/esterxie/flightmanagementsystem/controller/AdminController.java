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

	/**
	 * This controller is taking us to the admin dashboard along with the details
	 * that we are adding in the model to be used on that page.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("flightsList", flightService.getAllFlightsSorted());
		return "flights";
	}
}
