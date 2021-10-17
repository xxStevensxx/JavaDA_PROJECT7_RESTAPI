package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

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
public class CurveController {
	@Autowired
	CurvePointRepository curveRepository;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
    	model.addAttribute("curvePoints", curveRepository.findAll());
        	return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
			curveRepository.save(curvePoint);
        
			Application.LOG.info("curvePoint id: " + curvePoint.getCurveId() + " Was save ");

			model.addAttribute("curvePoints", curveRepository.findAll());
				return "redirect:/curvePoint/list";
		}
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curve = curveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
      
		Application.LOG.info("curvePoint id: " + curve.getCurveId() + " Was show in form " );

    	model.addAttribute("curvePoint", curve);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		return "curvePoint/update";
    		
		}
    	
    	curvePoint.setId(id);
    	curveRepository.save(curvePoint);
      
		Application.LOG.info("curvePoint id: " + curvePoint.getCurveId() + " Was update ");

    	model.addAttribute("curvePoint", curveRepository.findAll());
    		return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curve = curveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    	curveRepository.delete(curve);
      
		Application.LOG.info("curvePoint id: " + curve.getCurveId() + " Was delete ");
   
    	model.addAttribute("curvePoints", curveRepository.findAll());
        	return "redirect:/curvePoint/list";
    }
}
