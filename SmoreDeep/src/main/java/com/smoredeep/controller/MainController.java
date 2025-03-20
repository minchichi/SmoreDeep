package com.smoredeep.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smoredeep.entity.TbCourse;
import com.smoredeep.service.LectureListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/lecture_one")
	public String lecture_one() {
		return "lecture_one";
	}

//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}
	
	@GetMapping("/chatbot")
	public String chatbot() {
		return "chatbot";
	}
	
	@GetMapping("/")
	public String lecture_list() {
		return "lecture_list";
	}
	
	private final LectureListService lectureListService;
	
	@GetMapping("/test")
	public String lecture_list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<TbCourse> paging = this.lectureListService.getList(page);
		model.addAttribute("paging", paging);
		
		return "lecture_list_test";
	}

	
	
}
