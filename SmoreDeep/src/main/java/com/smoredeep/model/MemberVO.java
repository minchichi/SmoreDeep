package com.smoredeep.model;

import com.smoredeep.entity.TbUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
	
	private String id;
	private String pw;
	private String course_category;
	private String course_level;
	private String course_tm;
	
	
	public static MemberVO toMemberVO(TbUser tbuser) {
    	MemberVO vo = new MemberVO();
    	
    	vo.setId(tbuser.getUserId());
    	vo.setPw(tbuser.getUserPw());
    	vo.setCourse_category(tbuser.getCourseCategory());
    	vo.setCourse_level(tbuser.getCourseLevel());
    	vo.setCourse_tm(tbuser.getCourseTm());
    	
    	return vo;
    }
	
	
}
