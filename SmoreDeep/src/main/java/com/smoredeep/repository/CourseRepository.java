package com.smoredeep.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smoredeep.entity.TbCourse;


@Repository
public interface CourseRepository extends JpaRepository<TbCourse, Integer> {
	TbCourse findByCourseIdx(int course_idx);
    Page<TbCourse> findAll(Pageable pageable);
    
    @Query(value = "select * from tb_course where course_hide=0", nativeQuery = true)
    Page<TbCourse> findAllExp(Pageable pageable);
    
    @Query(value = "select distinct course_level from tb_course order by field (course_level, '입문', '초급', '중급이상')", nativeQuery = true)
    List<String> findDistinctCourseLevel();
    
    @Transactional
    @Modifying
    @Query(value = "update tb_course set course_hide = :courseHide where course_idx = :courseIdx", nativeQuery = true)
    void updateCourseHide(@Param("courseIdx") Integer courseIdx, @Param("courseHide") Integer courseHide);
    
}
