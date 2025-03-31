package com.smoredeep.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_preference")
@Data
public class TbPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prefIdx;

    private String userId;
    private String middleCategory;
    private String courseLevel;
    private String coursePlaytime;
    private String userText;

    private LocalDateTime createdAt;
}