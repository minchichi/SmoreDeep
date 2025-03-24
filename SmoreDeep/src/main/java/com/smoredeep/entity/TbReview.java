package com.smoredeep.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TbReview {
    
    @Id
    private int reviewIdx;			// 수강평 식별자
    private String reviewWriter;	// 리뷰 작성자
    private int courseIdx;			// 강의 식별자
    private String reviewContent;	// 리뷰 내용
    private int courseRatings;		// 강의 평점
    private Timestamp createdAt;	// 작성 일자
    private String sentiment;		// 긍정/부정
       
}
