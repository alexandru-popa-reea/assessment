package com.nia.assessment.controller;

import com.nia.assessment.dto.ResponseDto;
import com.nia.assessment.dto.SurveyDto;
import com.nia.assessment.service.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping(value = "/surveys", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto> saveNewSurvey(@RequestBody SurveyDto surveyDto) {
        try {
            surveyService.save(surveyDto);
            ResponseDto responseDto = ResponseDto.builder().status(200).message("SUCCESS").payload(null).build();
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().status(500).message("ERROR").payload(e.getMessage()).build();
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}
