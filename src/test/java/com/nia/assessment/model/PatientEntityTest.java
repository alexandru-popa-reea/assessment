package com.nia.assessment.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

class PatientEntityTest {

    @DisplayName("Test that a PatientEntity is created and its constructor functions correctly")
    @Test
    void test_PatientEntity_constructor() {

        //given
        PatientEntity patientEntity = new PatientEntity(77L, "Maria", "Ivanovna", (short) 55, List.of());

        //then //when
        then(patientEntity).isNotNull();
        then(patientEntity.getId()).isEqualTo(77L);
        then(patientEntity.getFirstName()).isEqualTo("Maria");
        then(patientEntity.getLastName()).isEqualTo("Ivanovna");
        then(patientEntity.getAge()).isEqualTo((short) 55);
        then(patientEntity.getSurveys()).isEmpty();
    }

    @DisplayName("Test that a PatientEntity is created and its methods (getters, setters) function correctly")
    @Test
    void test_PatientEntity_getters_and_setters() {

        //given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(22L);
        patientEntity.setFirstName("Maria");
        patientEntity.setLastName("Ivanovna");
        patientEntity.setAge((short) 55);
        patientEntity.setSurveys(List.of());

        //then //when
        then(patientEntity).isNotNull();
        then(patientEntity.getId()).isEqualTo(22L);
        then(patientEntity.getFirstName()).isEqualTo("Maria");
        then(patientEntity.getLastName()).isEqualTo("Ivanovna");
        then(patientEntity.getAge()).isEqualTo((short) 55);
        then(patientEntity.getSurveys()).isEmpty();
    }

    @DisplayName("Test that a PatientEntity is created and its methods (equals, hashCode) function correctly")
    @Test
    void test_PatientEntity_equals_and_hashCode() {

        //given
        PatientEntity patientEntity1 = PatientEntity.builder().id(41L).firstName("Maria").lastName("Ivanovna").age((short) 49).surveys(List.of()).build();
        PatientEntity patientEntity2 = PatientEntity.builder().id(41L).firstName("Maria").lastName("Ivanovna").age((short) 49).surveys(List.of()).build();

        //then //when
        then(patientEntity1).isEqualTo(patientEntity2);

        //just to illustrate normal assertions from Assertj
        //a.k.a. not the BDD ones
        assertThat(patientEntity1.hashCode() == patientEntity2.hashCode()).isTrue();
        assertThat(patientEntity1.equals(patientEntity2)).isTrue();
    }

}