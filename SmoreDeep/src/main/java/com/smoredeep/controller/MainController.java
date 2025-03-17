package com.smoredeep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/main")
	public String index() {
		return "main";
	}
	
	@GetMapping("/")
	public String lecture_list() {
		return "lecture-list";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/join2")
	public String join2() {
		return "join2";
	}
	
	
}
