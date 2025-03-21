package com.smoredeep.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoredeep.entity.TbCourse;
import com.smoredeep.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureListService {

	private final CourseRepository courseRepository;

	public Page<TbCourse> getList(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.courseRepository.findAll(pageable);
	}
	
	public Page<TbCourse> getListLevel(int page, String btnValue) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.courseRepository.findByCourseLevel(btnValue, pageable);
	}
	
	
}
