package com.smoredeep.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_recommendation")
@Data
public class TbRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recoIdx;

    private String userId;
    private Integer courseIdx;
    private Integer prefIdx;

    private Integer recommended;
    private LocalDateTime createdAt;
}