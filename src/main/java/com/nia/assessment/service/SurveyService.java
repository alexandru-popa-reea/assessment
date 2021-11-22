package com.nia.assessment.service;

import com.nia.assessment.dto.SurveyDto;
import com.nia.assessment.model.SurveyEntity;

import java.util.List;

public interface SurveyService {

    List<SurveyEntity> findAll();
    SurveyEntity findById(Long id);
    SurveyEntity save(SurveyEntity surveyEntity);
    SurveyEntity save(SurveyDto surveyDto);
}
