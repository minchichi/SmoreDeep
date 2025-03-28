package com.smoredeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoredeep.entity.TbPreference;

@Repository
public interface PreferenceRepository extends JpaRepository<TbPreference, Long> {
    
}