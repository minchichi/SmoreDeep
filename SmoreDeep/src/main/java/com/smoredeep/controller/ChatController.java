package com.smoredeep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smoredeep.model.PreferenceRequestDTO;
import com.smoredeep.model.SingleRecommendationDTO;
import com.smoredeep.repository.CourseRepository;
import com.smoredeep.service.ChatbotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private RestTemplate restTemplate;  

    @PostMapping("/recommend")
    public ResponseEntity<?> recommendLecture(@RequestBody Map<String, String> payload) {
        String userInput = payload.get("input");

        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("input", userInput);
        
        String colabUrl = "https://26cc-34-106-23-224.ngrok-free.app/recommend"; 

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(colabUrl, request, Map.class);

            // FastAPI에서 추천받은 강의명 리스트
            List<String> titles = (List<String>) response.getBody().get("titles");

            // 임시 출력 확인용
            System.out.println("추천 강의명: " + titles);

            // 그대로 HTML로 보낼 경우
            return ResponseEntity.ok(Map.of("message", "추천 완료", "results", titles));

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
   
}
