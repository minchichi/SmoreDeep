package com.smoredeep.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smoredeep.entity.QTbCourse;
import com.smoredeep.entity.TbCourse;

@Repository
public class CourseRepositorySupport extends QuerydslRepositorySupport {
	private final JPAQueryFactory queryFactory;
		
	public CourseRepositorySupport(JPAQueryFactory queryFactory) {
		super(TbCourse.class);
		this.queryFactory = queryFactory;
	}
	
	public Page<TbCourse> findCourse(int hide, String search, String middleCategory, String courseLevel, String courseTm, Pageable pageable) {
		List<TbCourse> result = queryFactory
				.selectFrom(QTbCourse.tbCourse)
				.where(
						QTbCourse.tbCourse.courseHide.eq(hide),
						search(search),
						eqMiddleCategory(middleCategory),
						eqCourseLevel(courseLevel),
						compareCourseTm(courseTm)
						)
//				.orderBy(QTbCourse.tbCourse.enrolledStudents.desc())
				.orderBy(QTbCourse.tbCourse.courseIdx.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		long totalCount = queryFactory
				.select(QTbCourse.tbCourse.count())
				.from(QTbCourse.tbCourse)
				.where(
						QTbCourse.tbCourse.courseHide.eq(hide),
						search(search),
						eqMiddleCategory(middleCategory),
						eqCourseLevel(courseLevel),
						compareCourseTm(courseTm)
						)
				.fetchFirst();
		
		return PageableExecutionUtils.getPage(result, pageable, () ->
		totalCount);
//		return new PageImpl<>(result, pageable, totalCount);
	}
	
	
	private BooleanExpression search(String search) {
		if (search==null||search.isEmpty()) {
			return null;
		}
		return QTbCourse.tbCourse.courseNm.contains(search)
				.or(QTbCourse.tbCourse.targetAudience.contains(search))
				.or(QTbCourse.tbCourse.courseTm.contains(search))				
				.or(QTbCourse.tbCourse.courseLevel.contains(search))
				.or(QTbCourse.tbCourse.mainCategory.contains(search))
				.or(QTbCourse.tbCourse.middleCategory.contains(search))
				.or(QTbCourse.tbCourse.subCategory.contains(search));
	}
	
	private BooleanExpression eqMiddleCategory(String middleCategory) {
		if (middleCategory==null||middleCategory.isEmpty()||middleCategory.equals("")||middleCategory.equals("전체")) {
			return null;
		}
		return QTbCourse.tbCourse.middleCategory.eq(middleCategory);
	}
	
	private BooleanExpression eqCourseLevel(String courseLevel) {
		if (courseLevel==null||courseLevel.isEmpty()||courseLevel.equals("난이도")||courseLevel.equals("")||courseLevel.equals("전체")) {
			return null;
		}
		return QTbCourse.tbCourse.courseLevel.eq(courseLevel);
	}
	
	private BooleanExpression compareCourseTm(String courseTm) {
		
		QTbCourse qcourse = QTbCourse.tbCourse;
		StringTemplate timeExtract = Expressions.stringTemplate(
				"CASE WHEN locate('시간', {0}) > 0 THEN CAST(SUBSTRING_INDEX(SUBSTRING_INDEX({0}, '시간', 1), '(', -1) AS STRING) ELSE '0' END", 
	            qcourse.courseTm);
		
		if (courseTm==null||courseTm.isEmpty()||courseTm.equals("강의시간")||courseTm.equals("")||courseTm.equals("전체")) {
			return null;
		} else if (courseTm.equals("2시간 미만")) {
			return timeExtract.castToNum(Integer.class).loe(1);
		} else if (courseTm.equals("4시간 미만")) {
			return timeExtract.castToNum(Integer.class).loe(3);
		} else if (courseTm.equals("8시간 미만")) {
			return timeExtract.castToNum(Integer.class).loe(7);
		} else if (courseTm.equals("12시간 미만")) {
			return timeExtract.castToNum(Integer.class).loe(11);
		} else {
			return timeExtract.castToNum(Integer.class).goe(12);
		} 
		
	}
	
	
}
