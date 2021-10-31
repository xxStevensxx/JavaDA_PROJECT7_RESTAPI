package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

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
public class TradeController {

	public final Logger log = LogManager.getLogger(TradeController.class.getName());

	@Autowired
	TradeRepository tradeRepository;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trades", tradeRepository.findAll());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addUser(Trade bid) {
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			tradeRepository.save(trade);

			log.info("trade id: " + trade.getTradeId() + " Was save ");

			model.addAttribute("trades", tradeRepository.findAll());
			return "redirect:/trade/list";
		}
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));

		log.info("trade id: " + trade.getTradeId() + " Was show in form ");

		model.addAttribute("trade", trade);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "trade/update";
		}

		trade.setTradeId(id);
		tradeRepository.save(trade);

		log.info("trade id: " + trade.getTradeId() + " Was update ");

		model.addAttribute("trade", tradeRepository.findAll());
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {

		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		tradeRepository.delete(trade);

		log.info("trade id: " + trade.getTradeId() + " Was delete ");

		model.addAttribute("trades", tradeRepository.findAll());
		return "redirect:/trade/list";
	}
}
