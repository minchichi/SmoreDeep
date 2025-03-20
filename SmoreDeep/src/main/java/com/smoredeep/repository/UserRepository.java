package com.smoredeep.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbUser;


@Repository
public interface UserRepository extends JpaRepository<TbUser, String> {
    public Optional<TbUser> findByUserId(String user_id);
}
