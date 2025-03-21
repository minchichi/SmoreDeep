package com.smoredeep.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbCourse;



@Repository
public interface CourseRepository extends JpaRepository<TbCourse, Integer> {
    public Optional<TbCourse> findByCourseIdx(int course_idx);
    Page<TbCourse> findAll(Pageable pageable);
    Page<TbCourse> findByCourseLevel(String search_level, Pageable pageable);
}
