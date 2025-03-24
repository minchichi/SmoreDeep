package com.smoredeep.google;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.smoredeep.entity.TbUser;
import com.smoredeep.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final HttpSession session;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String provider = userRequest.getClientRegistration().getClientId();
        String provider = "google";
//        String username = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String role = "0"; //일반 유저
        Optional<TbUser> findUser = userRepository.findByUserId(email);
        if (findUser.isEmpty()) {
            TbUser user = TbUser.builder()
                    .email(email)
                    .password(encoder.encode("password"))
//                    .name(username)
                    .role(role)
                    .provider(provider).build();
            userRepository.save(user);
        }
        
        session.setAttribute("user", findUser);
        
        return oAuth2User;
    }
}