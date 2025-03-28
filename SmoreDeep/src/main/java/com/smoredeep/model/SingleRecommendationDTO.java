package com.smoredeep.model;

import lombok.Data;

@Data
public class SingleRecommendationDTO {
    private String userId;
    private Integer prefIdx;
    private Integer courseIdx; // null이면 추천 실패
}