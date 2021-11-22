package com.nia.assessment.service.impl;

import com.nia.assessment.dto.SurveyDto;
import com.nia.assessment.model.SurveyEntity;
import com.nia.assessment.repository.PatientRepository;
import com.nia.assessment.repository.SurveyRepository;
import com.nia.assessment.service.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<SurveyEntity> findAll() {
        return surveyRepository.findAll();
    }

    @Override
    public SurveyEntity findById(@NotNull Long id) {
        Optional<SurveyEntity> optionalSurveyEntity = surveyRepository.findById(id);
        if (optionalSurveyEntity.isPresent()) {
            return optionalSurveyEntity.get();
        } else {
            throw new RuntimeException(format("Survey with id '%d' was not foud", id));
        }
    }

    @Override
    public SurveyEntity save(SurveyEntity surveyEntity) {
        return surveyRepository.save(surveyEntity);
    }

    @Override
    public SurveyEntity save(SurveyDto surveyDto) {
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setPatient(patientRepository.getById(surveyDto.getPatientId()));
        surveyEntity.setCreatedDate(new Date());
        surveyEntity.setSleepGrade(surveyDto.getSleepGrade());
        surveyEntity.setSkinGrade(surveyDto.getSkinGrade());
        return surveyRepository.save(surveyEntity);
    }
}
