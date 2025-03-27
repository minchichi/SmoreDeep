package com.smoredeep.kakao;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smoredeep.entity.TbUser;
import com.smoredeep.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	KakaoAPI kakaoApi = new KakaoAPI();

	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/login")
	public String login(@RequestParam("code") String code, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 1번 인증코드 요청 전달
		String accessToken = kakaoApi.getAccessToken(code);
		// 2번 인증코드로 토큰 전달
		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

		System.out.println("login info : " + userInfo.toString());

		System.out.println(userInfo);

		if (userInfo.get("email") != null) {
			session.setAttribute("userId", userInfo.get("email"));
			session.setAttribute("accessToken", accessToken);
		}
		mav.addObject("userId", userInfo.get("email"));
		mav.setViewName("lecture_list");

		System.out.println(userInfo);

		String email = userInfo.get("email").toString();
//		String username = userInfo.get("nickname").toString();
        String course_category = "";
        String course_level = "";
        String course_tm = "";
		String role = "0";
		String provider = "kakao";

		Optional<TbUser> findUser = userRepository.findByUserId(email);
		if (findUser.isEmpty()) {
			TbUser user = TbUser.builder()
					.email(email)
					.password(encoder.encode("password"))
//					.name(username)
					.course_category(course_category)
                    .course_level(course_level)
                    .course_tm(course_tm)
					.role(role)
					.provider(provider).build();
			userRepository.save(user);
		}
		
		findUser = userRepository.findByUserId(email);
		System.out.println(findUser);
		session.setAttribute("user", findUser);

		return "redirect:/lecture_list";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
		session.removeAttribute("accessToken");
		session.removeAttribute("userId");
		session.removeAttribute("user");
		mav.setViewName("lecture_list");
		return "redirect:/";
	}

}