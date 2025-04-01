package com.smoredeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbRecommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<TbRecommendation, Long> {
	
	@Query(value = "select * from tb_recommendation where user_id=:userId and recommended=0 order by created_at desc limit 1", nativeQuery = true)
    TbRecommendation findRecommended0(@Param("userId") String userId);
	
}