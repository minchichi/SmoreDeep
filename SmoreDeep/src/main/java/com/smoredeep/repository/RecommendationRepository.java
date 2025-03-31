package com.smoredeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbRecommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<TbRecommendation, Long> {
}