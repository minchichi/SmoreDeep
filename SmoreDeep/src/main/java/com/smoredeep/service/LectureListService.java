package com.smoredeep.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoredeep.entity.TbCourse;
import com.smoredeep.repository.CourseRepository;
import com.smoredeep.repository.CourseRepositorySupport;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureListService {

	private final CourseRepository courseRepository;
	private final CourseRepositorySupport courseRepositorySupport;

	public Page<TbCourse> getListExp(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.courseRepository.findAllExp(pageable);
	}	
	
	public Page<TbCourse> getList(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.courseRepository.findAll(pageable);
	}
	
	public Page<TbCourse> getList(int hide, String search, String category, String level, String schedule, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.courseRepositorySupport.findCourse(hide, search, category, level, schedule, pageable);
	}
	
}
