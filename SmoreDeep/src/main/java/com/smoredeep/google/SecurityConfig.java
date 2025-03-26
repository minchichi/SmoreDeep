package com.smoredeep.google;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable())  // CSRF 필터 해제 
            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/private/**").authenticated()  // private로 시작하는 URI는 로그인 필수
//                .requestMatchers("/admin/**").hasRole("1")  // admin으로 시작하는 URI는 관리자만 접근 가능
                .anyRequest().permitAll()  // 나머지 URI는 모두 접근 허용
            )
            .oauth2Login(oauth -> oauth
                .loginPage("/loginForm")  // 로그인이 필요한데 로그인을 하지 않았다면 이동할 URI 설정
                .defaultSuccessUrl("/")  // OAuth 구글 로그인이 성공하면 이동할 URI 설정
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2MemberService))  // 로그인 완료 후 회원 정보 받기
            )
            .formLogin(Customizer.withDefaults())  // 기본 form login 설정
            .logout(logout -> logout
                .logoutUrl("/logout")  // 로그아웃 URL 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // 로그아웃 요청 URL
                .logoutSuccessUrl("/loginForm")  // 로그아웃 성공 후 이동할 URI 설정
                .logoutSuccessHandler((request, response, authentication) -> {
                	// 로그아웃 후 쿠키 삭제 명시적으로 추가
                	Cookie cookie = new Cookie("JSESSIONID", null);
                    cookie.setMaxAge(0);  // 쿠키의 유효 시간을 0으로 설정하여 즉시 삭제
                    cookie.setPath("/");  // 모든 경로에서 쿠키 삭제되도록 설정
                    cookie.setHttpOnly(true);  // 보안을 위해 HttpOnly로 설정
                    cookie.setSecure(true);  // HTTPS에서만 쿠키 전송되도록 설정 (필요 시)
                    response.addCookie(cookie);  // 쿠키를 응답에 추가
                    response.sendRedirect("/loginForm");
                })
                .deleteCookies("JSESSIONID")  // 로그아웃 시 삭제할 쿠키 설정
                .invalidateHttpSession(true)  // 세션 무효화 설정
                .clearAuthentication(true)  // 인증 정보 초기화
                .addLogoutHandler((request, response, authentication) -> {})  // 추가 로그아웃 핸들러
                .permitAll()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 세션을 사용할 필요가 있으면 사용
            );
        
        return http.build();
    }
    
    
    
}