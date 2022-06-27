package com.esterxie.flightmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esterxie.flightmanagementsystem.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/flight/delete/{id}")
	public String deleteBookedFlight(@PathVariable(name = "id") String flightNumber, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		userService.deleteBookedFlight(flightNumber, email);
		model.addAttribute("flightsList", userService.getSpecificUserFlights(email));
		return "userflights";
	}

	@GetMapping("/flights")
	public String specificUserFlights(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		model.addAttribute("flightsList", userService.getSpecificUserFlights(email));
		return "userflights";
	}

}
