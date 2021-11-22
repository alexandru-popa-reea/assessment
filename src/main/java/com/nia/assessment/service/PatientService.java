package com.nia.assessment.service;

import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.repository.PatientRepository;

import java.util.List;

public interface PatientService {
    PatientEntity savePatient(PatientEntity userEntity);
    void deletePatient(Long id);
    List<PatientEntity> getAllPatients();
    List<PatientEntity> findAllByFirstNameIn(List<String> firstNamesList);
}
