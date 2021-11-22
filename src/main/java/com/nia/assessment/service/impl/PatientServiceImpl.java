package com.nia.assessment.service.impl;

import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.repository.PatientRepository;
import com.nia.assessment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepo;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public PatientEntity savePatient(PatientEntity userEntity) {
        return null;
    }

    @Override
    public void deletePatient(Long id) {

    }

    @Override
    public List<PatientEntity> getAllPatients() {
        return patientRepo.findAll();
    }

    @Override
    public List<PatientEntity> findAllByFirstNameIn(List<String> firstNamesList) {
        return patientRepo.findAllByFirstNameIn(firstNamesList);
    }
}
