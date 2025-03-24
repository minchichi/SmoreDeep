package com.smoredeep.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbCourse;

@Repository
public interface CourseRepository extends JpaRepository<TbCourse, Integer> {
	TbCourse findByCourseIdx(int course_idx);
    Page<TbCourse> findAll(Pageable pageable);
    
    @Query(value = "select distinct course_level from tb_course order by field (course_level, '입문', '초급', '중급이상')", nativeQuery = true)
    List<String> findDistinctCourseLevel();
    
}
