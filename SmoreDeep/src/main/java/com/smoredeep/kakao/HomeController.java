package com.smoredeep.kakao;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.smoredeep.entity.TbUser;
import com.smoredeep.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
		String password = userInfo.get("email").toString();
		String username = userInfo.get("nickname").toString();
		String role = "0";
		String provider = "kakao";

		Optional<TbUser> findUser = userRepository.findByUserId(email);
		if (findUser.isEmpty()) { // 찾지 못했다면
			TbUser user = TbUser.builder()
					.email(email)
					.password(encoder.encode("password"))
//					.name(username)
					.role(role)
					.provider(provider).build();
			userRepository.save(user);
		}
		
		session.setAttribute("user", userInfo);

		return "redirect:/";
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