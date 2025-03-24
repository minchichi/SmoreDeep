package com.smoredeep.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TbCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseIdx;			// 강의 식별자
	private String courseSite;		// 강의 사이트명
	private String courseNm;		// 강의 명
	private int enrolledStudents;	// 수강생 수
	private String targetAudience;	// 강의 대상
	private String courseTm;		// 강의 시간
	private String courseLevel;		// 강의 난이도
	private String courseImg;		// 강의 이미지
	private String courseUrl;		// 강의 URL
	private String mainCategory;	// 대분류
	private String middleCategory;	// 중분류
	private String subCategory;		// 소분류
	private Double positiveRatio;	// 긍정비율
	private Double averageRating;	// 평균 별점
	private String courseIntro;		// 강의 소개
}
