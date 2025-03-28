package com.smoredeep.model;

import lombok.Data;

@Data
public class PreferenceRequestDTO {
    private String userId;
    private String middleCategory;
    private String courseLevel;
    private String coursePlaytime;
    private String userText; 
}