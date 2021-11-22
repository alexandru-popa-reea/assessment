package com.nia.assessment.repository;

import com.nia.assessment.model.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    List<SurveyEntity> findAllByPatientFirstNameAndPatientLastNameAndPatientAge(
            String patientFirstName,
            String patientLastName,
            short patientAge
    );

    List<SurveyEntity> findAllByCreatedDate(Date createdDate);
}
