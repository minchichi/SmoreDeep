package com.smoredeep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {

	private int course_idx;			// 강의 식별자
	private String course_site;		// 강의 사이트명
	private String course_nm;		// 강의 명
	private int enrolled_students;	// 수강생 수
	private String target_audience;	// 강의 대상
	private String course_tm;		// 강의 시간
	private String course_level;	// 강의 난이도
	private String course_img;		// 강의 이미지
	private String course_url;		// 강의 URL
	private String main_category;	// 대분류
	private String middle_category;	// 중분류
	private String sub_category;	// 소분류
	
	
}
