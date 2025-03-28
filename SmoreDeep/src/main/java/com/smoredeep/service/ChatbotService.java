package com.smoredeep.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.smoredeep.entity.TbPreference;
import com.smoredeep.entity.TbRecommendation;
import com.smoredeep.model.PreferenceRequestDTO;
import com.smoredeep.model.SingleRecommendationDTO;
import com.smoredeep.repository.PreferenceRepository;
import com.smoredeep.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final PreferenceRepository preferenceRepository;
    private final RecommendationRepository recommendationRepository;

    public Integer savePreference(PreferenceRequestDTO dto) {
        TbPreference pref = new TbPreference();
        pref.setUserId(dto.getUserId());
        pref.setMiddleCategory(dto.getMiddleCategory());
        pref.setCourseLevel(dto.getCourseLevel());
        pref.setCoursePlaytime(dto.getCoursePlaytime());
        pref.setUserText(dto.getUserText());
        pref.setCreatedAt(LocalDateTime.now());

        TbPreference saved = preferenceRepository.save(pref);
        return saved.getPrefIdx(); // 선호 식별자 반환
    }

    public void saveSingleRecommendation(SingleRecommendationDTO dto) {
        TbRecommendation r = new TbRecommendation();
        r.setUserId(dto.getUserId());
        r.setPrefIdx(dto.getPrefIdx());
        r.setCourseIdx(dto.getCourseIdx());
        r.setRecommended(dto.getCourseIdx() == null ? 0 : 1);
        r.setCreatedAt(LocalDateTime.now());

        recommendationRepository.save(r);
    }
}
