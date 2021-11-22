package com.nia.assessment.service.impl;

import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.model.SurveyEntity;
import com.nia.assessment.repository.SurveyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SurveyServiceImplTest {

    @InjectMocks
    SurveyServiceImpl surveyService;

    @Mock
    private SurveyRepository surveyRepository;

    @Test
    void test_findAll_calls_SurveyRepository() {

        //given
        //when we invoke the service, the repository will be called

        //when
        surveyService.findAll();

        //then
        verify(surveyRepository, times(1)).findAll();
    }

    @Test
    void test_saving_a_SurveyEntity_calls_SurveyRepository_Save() {

        //given
        PatientEntity patientEntity = PatientEntity.builder().firstName("John").lastName("Ferguson").age((short) 24).build();
        SurveyEntity surveyEntity = SurveyEntity.builder().patient(patientEntity).createdDate(new Date()).skinGrade((short) 6).sleepGrade((short) 8).build();

        //when
        surveyService.save(surveyEntity);

        //then
        verify(surveyRepository, times(1)).save(any(SurveyEntity.class));
    }

}