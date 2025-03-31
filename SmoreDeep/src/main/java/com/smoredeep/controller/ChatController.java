package com.smoredeep.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smoredeep.entity.TbCourse;
import com.smoredeep.entity.TbRecommendation;
import com.smoredeep.model.PreferenceRequestDTO;
import com.smoredeep.model.SingleRecommendationDTO;
import com.smoredeep.repository.CourseRepository;
import com.smoredeep.repository.RecommendationRepository;
import com.smoredeep.service.ChatbotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/recommend")
    public ResponseEntity<?> recommendLecture(@RequestBody Map<String, String> payload) {
        String userInput = payload.get("input");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestJson = Map.of("input", userInput);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity("https://smoredeepchat.ngrok.io/recommend", request, Map.class);
            List<String> titles = (List<String>) response.getBody().get("titles");

            // DB에서 존재하는 강의만 필터링
            List<TbCourse> matchedCourses = courseRepository.findByCourseNmIn(titles);

            if (matchedCourses.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "적합한 강의가 없습니다.", "results", List.of()));
            }

            List<String> finalTitles = matchedCourses.stream()
                    .map(TbCourse::getCourseNm)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of("message", "추천 완료", "results", finalTitles));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("FastAPI 연결 실패: " + e.getMessage());
        }
    }
    
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/chatbot")
    public class ChatbotController {

        private final ChatbotService chatbotService;

        @PostMapping("/preference")
        public ResponseEntity<Integer> savePreference(@RequestBody PreferenceRequestDTO dto) {
            Integer prefIdx = chatbotService.savePreference(dto);
            return ResponseEntity.ok(prefIdx);
        }

        @PostMapping("/recommend/one")
        public ResponseEntity<String> saveRecommendation(@RequestBody SingleRecommendationDTO dto) {
            chatbotService.saveSingleRecommendation(dto);
            return ResponseEntity.ok("추천 결과 저장 완료");
        }
    }
   

    @Autowired
    private RecommendationRepository recommendationRepository;
    @PostMapping("/recommend/fail")
    public ResponseEntity<?> saveFailedRecommendation(@RequestBody Map<String, Object> payload) {
        try {
            Integer prefIdx = (Integer) payload.get("prefIdx");
            String userId = (String) payload.get("userId");

            TbRecommendation failRec = new TbRecommendation();
            failRec.setPrefIdx(prefIdx);
            failRec.setUserId(userId);
            failRec.setCourseIdx(null);  
            failRec.setRecommended(0);
            failRec.setCreatedAt(LocalDateTime.now());

            recommendationRepository.save(failRec);  
            return ResponseEntity.ok("추천 실패 기록 저장 성공");
        } catch (Exception e) {
            e.printStackTrace();  
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("추천 실패 기록 저장 실패: " + e.getMessage());
        }
    }
}