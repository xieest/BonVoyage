package com.esterxie.flightmanagementsystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * This controller is taking the user to the registration page.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	/**
	 * This controller is accepting the incoming parameters from the registration
	 * form.
	 *
	 * @param user
	 * @param result
	 * @return
	 */
	@PostMapping("/register")
	public String register(@Valid User user, BindingResult result) {

		if (!user.getEmail().equals(user.getRepeatedEmail())) {
			result.rejectValue("repeatedEmail", "error.repeatedEmail",
					"Kindly enter the same email that you entered above");
			return "register";
		}

		if (!user.getPassword().equals(user.getRepeatedPassword())) {
			result.rejectValue("repeatedPassword", "error.repeatedPassword",
					"Kindly enter the same password that you entered above");
			return "register";
		}

		if (result.hasErrors()) {
			return "register";
		} else if (userService.findUserByEmail(user.getEmail()).isPresent()) {
			result.rejectValue("email", "error.email", "There is already an account with this email");
			return "register";
		} else {
			userService.saveUser(user);
			return "redirect:/login";
		}
	}

	/**
	 * Renders the login page.
	 *
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Shows errors on the login page, if there are any.
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
	 * After a successful login, spring security will redirect the user to the
	 * homepage.
	 *
	 * @return
	 */
	@GetMapping("/login-success")
	public String loginSuccess() {
		return "homepage";
	}
}