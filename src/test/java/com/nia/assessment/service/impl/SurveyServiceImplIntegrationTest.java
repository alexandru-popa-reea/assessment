package com.nia.assessment.service.impl;

import com.nia.assessment.dto.SurveyDto;
import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.model.SurveyEntity;
import com.nia.assessment.repository.PatientRepository;
import com.nia.assessment.repository.SurveyRepository;
import com.nia.assessment.service.SurveyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Date;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
class SurveyServiceImplIntegrationTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SurveyService surveyService;

    @DisplayName("Verify that SurveyService retrieves correctly a SurveyEntity")
    @Test
    void getSurveyById_forSavedSurvey_isReturned() {

        //given
        PatientEntity patientEntity = PatientEntity.builder().firstName("John").lastName("Ferguson").age((short) 24).build();
        patientRepository.save(patientEntity);

        SurveyEntity surveyEntity = SurveyEntity.builder().patient(patientEntity).createdDate(new Date()).skinGrade((short) 6).sleepGrade((short) 8).build();
        surveyEntity.setPatient(patientEntity);
        surveyRepository.save(surveyEntity);

        //when
        SurveyEntity survey = surveyService.findById(surveyEntity.getId());

        //then
        then(survey.getId()).isNotNull();
        then(survey.getId()).isEqualTo(surveyEntity.getId());
        then(survey.getCreatedDate()).isEqualTo(surveyEntity.getCreatedDate());
        then(survey.getSkinGrade()).isEqualTo(surveyEntity.getSkinGrade());
        then(survey.getSleepGrade()).isEqualTo(surveyEntity.getSleepGrade());
        then(survey.getPatient().getId()).isEqualTo(surveyEntity.getPatient().getId());
        then(survey.getPatient().getAge()).isEqualTo(surveyEntity.getPatient().getAge());
        then(survey.getPatient().getFirstName()).isEqualTo(surveyEntity.getPatient().getFirstName());
        then(survey.getPatient().getLastName()).isEqualTo(surveyEntity.getPatient().getLastName());
    }

    @DisplayName("Verify that SurveyService saves correctly a SurveyDto")
    @Test
    void saveSurvey_saves_it_and_returns_it_back() {

        //given
        SurveyDto surveyDto = SurveyDto.builder().patientId(1L).skinGrade((short) 7).sleepGrade((short) 6).build();

        //when
        SurveyEntity savedSurvey = surveyService.save(surveyDto);

        //then
        then(savedSurvey.getId()).isNotNull();
        then(savedSurvey.getCreatedDate()).isNotNull();
        then(savedSurvey.getSkinGrade()).isEqualTo(surveyDto.getSkinGrade());
        then(savedSurvey.getSleepGrade()).isEqualTo(surveyDto.getSleepGrade());
        then(savedSurvey.getPatient().getId()).isEqualTo(surveyDto.getPatientId());
    }

}