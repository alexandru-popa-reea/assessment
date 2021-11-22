package com.nia.assessment.repository;

import com.nia.assessment.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    List<PatientEntity> findAllByFirstNameIn(List<String> firstNamesList);
    List<PatientEntity> findAllByFirstName(String firstName);
    List<PatientEntity> findAllByFirstNameAndLastNameAndAge(String firstName, String lastName, short age);

}
