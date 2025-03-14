package com.smoredeep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/main")
	public String index() {
		return "main";
	}
	
	@GetMapping("/lecture-list")
	public String lecture_list() {
		return "lecture-list";
	}
	
	
}
