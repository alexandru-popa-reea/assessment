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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PATIENTS")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "AGE")
    private short age;

    @OneToMany(mappedBy = "patient", cascade = MERGE)
    private List<SurveyEntity> surveys;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientEntity)) return false;
        PatientEntity that = (PatientEntity) o;
        return age == that.age && id.equals(that.id) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && surveys.equals(that.surveys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, surveys);
    }
}
