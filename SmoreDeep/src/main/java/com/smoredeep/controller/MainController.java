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
		return "lecture_list";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/lecture_one")
	public String lecture_one() {
		return "lecture_one";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/chatbot")
	public String chatbot() {
		return "chatbot";
	}
	
	
}
