package com.esterxie.flightmanagementsystem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.esterxie.flightmanagementsystem.model.User;
import com.esterxie.flightmanagementsystem.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String register(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		} else if (userService.findUserByEmail(user.getEmail()).isPresent()) {
			result.rejectValue("email", "error.email", "There is already an account with this email");
			return "register";
		} else {
			userService.saveUser(user);
			/*
			 * model.addAttribute("successful", true); model.addAttribute("user", new
			 * User());
			 */
			return "redirect:/login";
		}
	}

	/**
	 * This api is rendering the login page
	 *
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * This api will be used to show any errors on the frontend.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("error", true);
		return "login";
	}

	/**
	 * After successfully logging in, the request will come to this controller. Here
	 * we will identify if the logged in user is admin or customer. If the logged-in
	 * user is a normal user then he will be redirected to the customer dashboard.
	 * Else he will be redirected towards admin dashboard.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/login-success")
	public String loginSuccess(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<User> user = userService.findUserByEmail(email);
		if (user.isPresent() && user.get().getRole().equalsIgnoreCase("ROLE_ADMIN")) {
			return "redirect:/admin/dashboard";
		} else if (user.isPresent() && user.get().getRole().equalsIgnoreCase("ROLE_USER")) {
			return "redirect:/customer/dashboard";
		} else {
			model.addAttribute("error", true);
			return "login";
		}
	}
}
