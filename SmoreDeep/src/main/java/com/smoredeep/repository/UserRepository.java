package com.smoredeep.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smoredeep.entity.TbUser;


@Repository
public interface UserRepository extends JpaRepository<TbUser, String> {
    
	TbUser findByUserIdAndUserPw(String user_id, String user_pw);
	
	public Optional<TbUser> findByUserId(String user_id);
	

	 // 업데이트 쿼리
   @Modifying // 데이터베이스의 데이터를 수정하는 쿼리 
   @Transactional // 트랜잭션을 보장, 작업 성공 후 완료되면 커밋, 실패하면 롤백 
   @Query("UPDATE TbUser u SET u.courseCategory = :course_category, u.courseLevel = :course_level, u.courseTm= :course_tm "
   		+ "WHERE u.userId = :user_id")
   void updateUser(String user_id, String course_category, String course_level, String course_tm);
	
}
