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
	@Autowired
	RuleNameRepository ruleRepository;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
			ruleRepository.save(ruleName);
			Application.LOG.info("ruleName id: " + ruleName.getId() + " Was save ");
				return "redirect:/ruleName/list";
		}
        	return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	RuleName ruleName = ruleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		Application.LOG.info("ruleName id: " + ruleName.getId() + " Was show in form ");
    	model.addAttribute("ruleName", ruleName);
        	return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
			return "ruleName/update";
		}
    	ruleName.setId(id);
    	ruleRepository.save(ruleName);
		Application.LOG.info("ruleName id: " + ruleName.getId() + " Was update ");
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        	return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    	RuleName rule = ruleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    	ruleRepository.delete(rule);
		Application.LOG.info("ruleName id: " + rule.getId() + " Was delete ");
    	model.addAttribute("ruleNames", ruleRepository.findAll());
        	return "redirect:/ruleName/list";
    }
}
