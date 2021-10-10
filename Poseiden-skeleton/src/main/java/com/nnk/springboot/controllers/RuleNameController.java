package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

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
public class RuleNameController {
    // TODO: Inject RuleName service
	@Autowired
	RuleNameRepository ruleRepository;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
    	if (!result.hasErrors()) {
			ruleRepository.save(ruleName);
			Application.LOG.info("methode validate. ruleName id: " + ruleName.getId() + " Was save");
				return "redirect:/ruleName/list";
		}
        	return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
    	RuleName ruleName = ruleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		Application.LOG.info("methode showUpdateForm .ruleName id: " + ruleName.getId() + " Was show in form");
    	model.addAttribute("ruleName", ruleName);
        	return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
    	if (result.hasErrors()) {
			return "ruleName/update";
		}
    	ruleName.setId(id);
    	ruleRepository.save(ruleName);
		Application.LOG.info("methode updateRuleName. ruleName id: " + ruleName.getId() + " Was update");
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        	return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
    	RuleName rule = ruleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    	ruleRepository.delete(rule);
		Application.LOG.info("metohde deleteRuleName. ruleName id: " + rule.getId() + " Was delete");
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        	return "redirect:/ruleName/list";
    }
}
