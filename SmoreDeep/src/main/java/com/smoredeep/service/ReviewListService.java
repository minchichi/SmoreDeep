package com.smoredeep.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoredeep.entity.TbReview;
import com.smoredeep.repository.ReviewRepositorySupport;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewListService {

	private final ReviewRepositorySupport reviewRepositorySupport;

	public Page<TbReview> getList(int idx, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return this.reviewRepositorySupport.findReview(idx, pageable);
	}
	
}
