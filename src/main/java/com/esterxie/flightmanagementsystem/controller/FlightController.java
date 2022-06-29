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

	/**
	 * Fetches sorted flights and populates them on the flights page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String getAllFlights(Model model) {
		model.addAttribute("flightsList", flightService.getAllFlightsSorted());
		return "flights";
	}

	/**
	 * Deletes a flight based on its id.
	 *
	 * @param flightNumber
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String deleteFlight(@PathVariable(name = "id") String flightNumber) {
		flightService.deleteFlight(flightNumber);
		return "redirect:/admin/dashboard";
	}

	/**
	 * Search function. A user will be able to find a flight by city.
	 *
	 * @param keyword
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String searchFlightCity(@RequestParam(name = "keyword") String keyword, Model model) {
		model.addAttribute("flightsList", flightService.getFlightsByCity(keyword));
		return "flights";
	}

	/**
	 * Used to book a flight. When that book button will get hit, the request will
	 * come here. and then we will check which user did that, and then add that
	 * specific flights to his booked list.
	 *
	 * @param flightNumber
	 * @param model
	 * @return
	 */
	@GetMapping("/book/{id}")
	public String bookFlight(@PathVariable(name = "id") String flightNumber, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		flightService.bookFlight(flightNumber, email);
		model.addAttribute("flightsList", userService.getSpecificUserFlights(email));
		return "userflights";
	}
}