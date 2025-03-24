package com.smoredeep.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smoredeep.entity.QTbReview;
import com.smoredeep.entity.TbReview;

@Repository
public class ReviewRepositorySupport extends QuerydslRepositorySupport {
	private final JPAQueryFactory queryFactory;
		
	public ReviewRepositorySupport(JPAQueryFactory queryFactory) {
		super(TbReview.class);
		this.queryFactory = queryFactory;
	}
	
	public Page<TbReview> findReview(int idx, Pageable pageable) {
		List<TbReview> result = queryFactory
				.selectFrom(QTbReview.tbReview)
				.where(QTbReview.tbReview.courseIdx.eq(idx))
				.orderBy(QTbReview.tbReview.createdAt.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		long totalCount = queryFactory
				.select(QTbReview.tbReview.count())
				.from(QTbReview.tbReview)
				.where(QTbReview.tbReview.courseIdx.eq(idx))
				.fetchFirst();
		
		return PageableExecutionUtils.getPage(result, pageable, () ->
		totalCount);
	}
	
}
