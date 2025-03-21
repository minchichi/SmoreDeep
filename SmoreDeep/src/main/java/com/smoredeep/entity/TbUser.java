package com.smoredeep.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smoredeep.model.MemberVO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TbUser {
    
		
    @Id
    private String userId;			// 사용자 아이디
    private String userPw;			// 사용자 비밀번호
    private String courseCategory;	// 강의 카테고리
    private String courseLevel;		// 강의 난이도
    private String courseTm;		// 강의 시간
    private String adminIs;			// 관리자 여부
    @CreationTimestamp
    private Timestamp joinedAt;		// 가입 일자
    private String provider; 		// 공급자 (kakao, google)
    

    @Builder
    public TbUser(String email, String password, String role, String provider) {
        this.userId = email;
        this.userPw = password;
        this.adminIs = role;
        this.provider = provider;
    }

    
    @Builder
    public static TbUser toTbUser(MemberVO vo, BCryptPasswordEncoder encoder) {
    	TbUser tbuser = new TbUser();	
    	
    	tbuser.userId = vo.getId();
    	tbuser.userPw = encoder.encode(vo.getPw());
    	tbuser.courseCategory = vo.getCourse_category();
    	tbuser.courseLevel = vo.getCourse_level();
    	tbuser.courseTm = vo.getCourse_tm();
    	tbuser.adminIs = "0";
    	tbuser.provider = "smore";
    	
    	return tbuser;
    }
    
       
}
