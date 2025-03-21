package com.smoredeep.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smoredeep.entity.TbCourse;
import com.smoredeep.entity.TbUser;
import com.smoredeep.model.MemberVO;
import com.smoredeep.repository.UserRepository;
import com.smoredeep.service.LectureListService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	// 회원가입 
	@RequestMapping(value ="/submit_registration", method = RequestMethod.GET)
	public String join1() {	
		return "redirect:/main";
	}
	
	// 회원가입 DB저장 
	@RequestMapping(value ="/submit_registration", method = RequestMethod.POST)
	public String join2(MemberVO vo, BCryptPasswordEncoder encoder) {	
		TbUser tbUser = TbUser.toTbUser(vo, encoder);
		userRepository.save(tbUser);	
		return "redirect:/loginForm";
	}
	
    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }
	
	@Autowired
	UserRepository userRepository;
	
	// 로그인 기능 	
	@PostMapping("/submit_login_user")
	public String login(String user_id, String user_pw, HttpSession session, Model model, BCryptPasswordEncoder encoder) {			
//		TbUser tbUser = repo.findByUserIdAndUserPw(user_id, user_pw);
		Optional<TbUser> tbUser = userRepository.findByUserId(user_id);
		
		// 사용자 ID가 존재하지 않는 경우
		if (tbUser.isEmpty()) {
	        model.addAttribute("error", "아이디가 존재하지 않습니다.");
	        return "loginForm";
	    }

		// 비밀번호가 틀린 경우
	    if (!encoder.matches(user_pw, tbUser.get().getUserPw())) {
	        model.addAttribute("error", "비밀번호가 일치하지않습니다.");
	        return "loginForm";
	    }

	    // 로그인 성공: 세션에 사용자 정보 저장
	    session.setAttribute("user", tbUser);
	    return "redirect:/";
		
	}	
	
	// 로그아웃 기능
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return  "redirect:/";
		}
	
	
	private final LectureListService lectureListService;
	
	@GetMapping("/")
	public String lecture_list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<TbCourse> paging = this.lectureListService.getList(page);
		model.addAttribute("paging", paging);
		return "lecture_list";
	}
	
	
//	@GetMapping("/lecture_list.selectLevel")
//	@ResponseBody
//	public String lecture_list_level(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam("btnValue") String btnValue) {
//		Page<TbCourse> pagingLevel = this.lectureListService.getListLevel(page, btnValue);
//		model.addAttribute("paging", pagingLevel);
//		return "redirect:/";
//	}
	
	
		
	@GetMapping("/lecture_one")
	public String lecture_one() {
		return "lecture_one";
	}

	
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}
	
	@GetMapping("/chatbot")
	public String chatbot() {
		return "chatbot";
	}	

	


	
	
}
