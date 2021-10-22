package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

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
public class BidListController {
	
	
	public final Logger LOG =  LogManager.getLogger(BidListController.class.getName());
	
	@Autowired
	BidListRepository bidListRepository;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
    	model.addAttribute("bidLists", bidListRepository.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
			bidListRepository.save(bid);
			
			LOG.info("bid id: " + bid.getBidListId() + " Was save");

			model.addAttribute("bidLists", bidListRepository.findAll());
				return "redirect:/bidList/list";
		}
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
      
		LOG.info("bid id: " + bid.getBidListId() + " Was show in form ");

    	model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		return"bidList/update";
		}
    	
    	bidList.setBidListId(id);
    	bidListRepository.save(bidList);
      
		LOG.info("bid id: " + bidList.getBidListId() + " Was update");

    	model.addAttribute("bidList", bidListRepository.findAll());
    		return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
    	bidListRepository.delete(bid);
      
		LOG.info("bid id: " + bid.getBidListId() + " Was delete ");

    	model.addAttribute("bidLists", bidListRepository.findAll());
    		return "redirect:/bidList/list";
    }
}
