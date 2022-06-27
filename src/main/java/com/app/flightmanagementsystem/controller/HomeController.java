package com.app.flightmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Displays 
@Controller
public class HomeController {

	@GetMapping("/")
	public String homepage() {
		return "homepage";
	}

	@GetMapping("/shop")
	public String shop() {
		return "shop";
	}

	@GetMapping("/placesToVisit")
	public String placesToVisit() {
		return "placestovisit";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/shoppingcart")
	public String shoppingcart() {
		return "shoppingcart";
	}

}
