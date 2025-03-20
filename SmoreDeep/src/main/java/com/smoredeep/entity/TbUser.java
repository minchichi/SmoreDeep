package com.smoredeep.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TbUser {
       
    @Id
    private String userId;			// 사용자 아이디
    private String userPw;			// 사용자 비밀번호
    private String userName;		// 사용자 이름
    private String courseCategory;	// 강의 카테고리
    private String courseLevel;		// 강의 난이도
    private String adminIs;			// 관리자 여부
    @CreationTimestamp
    private Timestamp joinedAt;		// 가입 일자
    private String provider; 		// 공급자 (kakao, google)
    

    @Builder
    public TbUser(String email, String password, String name, String role, String provider) {
        this.userId = email;
        this.userPw = password;
        this.userName = name;
        this.adminIs = role;
        this.provider = provider;
    }
    
       
}
