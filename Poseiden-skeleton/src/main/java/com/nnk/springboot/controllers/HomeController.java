package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	
	public final Logger LOG =  LogManager.getLogger(HomeController.class.getName());

	
	@RequestMapping("/")
	public String home(Model model)
	{
		LOG.info("Home acces ");

		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		LOG.info("Admin home acces ");

		return "redirect:/bidList/list";
	}


}
