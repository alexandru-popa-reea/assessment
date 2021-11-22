package com.nia.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SURVEYS")
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "PATIENT_ID")
    private PatientEntity patient;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "SLEEP_GRADE")
    private short sleepGrade;

    @Column(name = "SKIN_GRADE")
    private short skinGrade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SurveyEntity)) return false;
        SurveyEntity that = (SurveyEntity) o;
        return sleepGrade == that.sleepGrade && skinGrade == that.skinGrade && id.equals(that.id) && patient.equals(that.patient) && createdDate.equals(that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, createdDate, sleepGrade, skinGrade);
    }
}
