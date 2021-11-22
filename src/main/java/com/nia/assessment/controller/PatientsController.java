package com.nia.assessment.controller;

import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientsController {

    private final PatientService patientService;

    @Autowired
    public PatientsController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(path = "/patients/")
    List<PatientEntity> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping(path = "/patients")
    List<PatientEntity> getAllPatientsByFirstName(@RequestParam List<String> firstNamesList) {
        return patientService.findAllByFirstNameIn(firstNamesList);
    }
}
