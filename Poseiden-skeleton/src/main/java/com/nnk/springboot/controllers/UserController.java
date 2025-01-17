package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SecurityConfig;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

	public final Logger log = LogManager.getLogger(UserController.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	SecurityConfig security;

	@RequestMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userRepository.findAll());
		log.info("List acces ");
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		log.info("Show panel for add user ");
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {

			user.setPassword(security.passwordEncoder().encode(user.getPassword()));
			userRepository.save(user);

			log.info("user id: " + user.getId() + " Was save ");

			model.addAttribute("users", userRepository.findAll());
			return "redirect:/user/list";
		}
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/update";
		}

		user.setPassword(security.passwordEncoder().encode(user.getPassword()));
		user.setId(id);
		userRepository.save(user);

		log.info("user id: " + user.getId() + " Was update");

		model.addAttribute("users", userRepository.findAll());
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);

		log.info("user id: " + user.getId() + " Was delete ");

		model.addAttribute("users", userRepository.findAll());
		return "redirect:/user/list";
	}
}
