package com.smoredeep.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smoredeep.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/find")
    public ResponseEntity<Map<String, Integer>> findByCourseName(@RequestParam String courseNm) {
        try {
            String decodedCourseNm = URLDecoder.decode(courseNm, "UTF-8");
            log.info("Decoded courseNm: {}", decodedCourseNm);
            return courseRepository.findByCourseNm(decodedCourseNm)
                    .map(course -> Map.of("courseIdx", course.getCourseIdx()))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", -1)));
        } catch (UnsupportedEncodingException e) {
            log.error("URL Decoding failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", -1));
        }
    }
}