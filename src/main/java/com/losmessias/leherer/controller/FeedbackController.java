package com.losmessias.leherer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.losmessias.leherer.domain.Feedback;
import com.losmessias.leherer.dto.FeedbackDto;
import com.losmessias.leherer.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@CrossOrigin
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/giveFeedback")
    public Feedback giveFeedback(@RequestBody FeedbackDto feedbackDto) {
        //TODO: check if class exists and if it has concluded
        //TODO: check if student has taken the class
        //TODO: check if professor has given the class
        return feedbackService.giveFeedback(feedbackDto);
    }

    @GetMapping("/getAllFeedbacks")
    public ResponseEntity<String> getAllFeedbacks() throws JsonProcessingException {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return ResponseEntity.ok(converter.getObjectMapper().writeValueAsString(feedbackService.getAllFeedbacks()));
    }
}
