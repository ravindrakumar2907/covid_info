package com.corona.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corona.services.CoronaVirusService;

import models.LocationStats;

@Controller
//@RestController
public class UIWebController {
	
	@Autowired
	CoronaVirusService coronaVirusService;
	
	@RequestMapping("/")
	//@GETMapping
	public String getIndexUIPage(Model model) {
		List<LocationStats> list= coronaVirusService.getListMainLocationStats();
		int totalcases = list.stream().mapToInt(stat-> stat.getLatesTotalCases()).sum();
		model.addAttribute("data", coronaVirusService.getListMainLocationStats());
		model.addAttribute("totalReportedCases", totalcases);
		return "index";
	}

}
