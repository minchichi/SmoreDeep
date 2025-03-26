package com.smoredeep.controller;


import java.util.List;
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

import com.smoredeep.entity.TbCourse;
import com.smoredeep.entity.TbReview;
import com.smoredeep.entity.TbUser;
import com.smoredeep.model.MemberVO;
import com.smoredeep.repository.CourseRepository;
import com.smoredeep.repository.UserRepository;
import com.smoredeep.service.LectureListService;
import com.smoredeep.service.ReviewListService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	
	private final LectureListService lectureListService;
	private final ReviewListService reviewListService;
	
	
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
	
	// 관리자 로그인 기능 	
	@PostMapping("/submit_login_admin")
	public String login(String admin_id, String admin_pw, HttpSession session, Model model) {			
		Optional<TbUser> tbUser = userRepository.findByUserId(admin_id);	
		System.out.println(admin_id);
		// 사용자가 존재하지 않는 경우
		if (tbUser.isEmpty()) {
			 model.addAttribute("error", "아이디가 존재하지 않습니다.");
			 return "loginForm";
	    }
		// 관리자가 아닌 경우
		else if (tbUser.get().getAdminIs().equals("0")) {
			  model.addAttribute("error", "관리자가 아닙니다.");
			  return "loginForm";
		}
		// 비밀번호가 틀린 경우
		else if (!tbUser.get().getUserPw().equals(admin_pw)) {
			  model.addAttribute("error", "비밀번호가 일치하지않습니다.");
			  return "loginForm";
			    } 
		else {
			    // 로그인 성공: 세션에 사용자 정보 저장
			 session.setAttribute("user", tbUser);
			    }
			    return "redirect:/admin_lecture";		
			}	

	
	// 관리자 강의관리 페이지
	@GetMapping("/admin_lecture")
	public String admin_lecture(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<TbCourse> paging = this.lectureListService.getList(page);
		model.addAttribute("adminpg", paging);
		return "admin_lecture";
	}
	
	
	@PostMapping("/admin_lecture_edit")
	public String admin_lecture_edit(@RequestParam("courseIdxList") List<Integer> courseIdxList,
			@RequestParam("courseHideList") List<Integer> courseHideList) {
		System.out.println(courseIdxList);
		System.out.println(courseHideList);
		for (int i = 0; i < courseIdxList.size(); i++) {
			Integer courseIdx = courseIdxList.get(i);
			Integer courseHide = courseHideList.get(i);
			System.out.println(courseIdx);
			System.out.println(courseHide);
			// 강의 업데이트
			courseRepository.updateCourseHide(courseIdx, courseHide);
		}
		return "redirect:/admin_lecture";
	}
		
		
	// 로그아웃 기능
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return  "redirect:/";
		}
		
	
	// 마이페이지 --> category, level, tm 수정 
	@PostMapping("/submit_mypage")
    public String updateUser(@RequestParam String user_id,
                             @RequestParam String course_category,
                             @RequestParam String course_level,
                             @RequestParam String course_tm) {
        // 직접 repository 메서드 호출
		userRepository.updateUser(user_id, course_category, course_level, course_tm);
        return "redirect:/";
    }

	
	@GetMapping("/")
	public String lecture_list(Model model, HttpSession session,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="category", required=false) String category,
			@RequestParam(value="level", required=false) String level,
			@RequestParam(value="schedule", required=false) String schedule) {
		List<String> course_level = courseRepository.findDistinctCourseLevel();
		model.addAttribute("course_level", course_level);
		int hide = 0;
		Page<TbCourse> paging = this.lectureListService.getList(hide, search, category, level, schedule, page);
		model.addAttribute("paging", paging);
		if (session.getAttribute("user")!=null) {
			Optional<TbUser> tbuser_login = (Optional<TbUser>) session.getAttribute("user");
			tbuser_login.ifPresent(user -> {
				String userId = user.getUserId();
			    Optional<TbUser> tbUser = userRepository.findByUserId(userId);
				session.setAttribute("user", tbUser);
			});
		}
		if(search!=null) {
			model.addAttribute("search", search);
		}
		if(category==null||category.equals("")) {
			model.addAttribute("category", "전체");
		} else {
			model.addAttribute("category", category);
		}
		if(level==null||level.equals("")) {
			model.addAttribute("level", "난이도");
		} else {
			model.addAttribute("level", level);
		}
		if(schedule==null||schedule.equals("")) {
			model.addAttribute("schedule", "강의시간");
		} else {
			model.addAttribute("schedule", schedule);
		}
		System.out.println(search);
		System.out.println(category);
		System.out.println(level);
		System.out.println(schedule);
		System.out.println(model.getAttribute("paging"));
		return "lecture_list";			
	}
	
	
	@GetMapping("/lecture_one")
	public String lecture_one(Model model,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="idx") int idx) {
		System.out.println("페이지: "+page);
		model.addAttribute("page", page);
		TbCourse courseOne = courseRepository.findByCourseIdx(idx);
		Page<TbReview> pagingReview = this.reviewListService.getList(idx, page);
		model.addAttribute("courseOne", courseOne);
		model.addAttribute("pagingReview", pagingReview);
		System.out.println(model.getAttribute("courseOne"));
		System.out.println(model.getAttribute("pagingReview"));
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
