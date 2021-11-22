package com.nia.assessment.repository;

import com.nia.assessment.model.PatientEntity;
import com.nia.assessment.model.SurveyEntity;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = NONE)
class PatientRepositoryTest {

    @Container
    @SuppressWarnings("rawtypes")
    private static final MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("nia")
            .withUsername("root")
            .withPassword("nia_mysql_pass")
            .withReuse(false);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @BeforeAll
    static void setUpDatabase() {
        Flyway.configure().dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword()).load().migrate();
    }

    @Test
    void contextLoads() {
       assertNotNull(testEntityManager);
       assertNotNull(entityManager);
       assertNotNull(dataSource);
       assertNotNull(patientRepository);
    }

    @Test
    void testGetPatient_Without_Survey_ByNameAndAge_returns_PatientDetails() {

        //given
        PatientEntity patientEntity = PatientEntity.builder().firstName("John").lastName("Ferguson").age((short) 24).build();
        PatientEntity patientSaved = testEntityManager.persistFlushFind(patientEntity);

        //when
        List<PatientEntity> patientsList = patientRepository.findAllByFirstNameAndLastNameAndAge("John", "Ferguson", (short) 24);

        //then
        then(patientsList).size().isEqualTo(1);
        PatientEntity patientSearched = patientsList.get(0);
        then(patientSearched.getId()).isNotNull();
        then(patientSearched.getFirstName()).isEqualTo(patientSaved.getFirstName());
        then(patientSearched.getLastName()).isEqualTo(patientSaved.getLastName());
        then(patientSearched.getAge()).isEqualTo(patientSaved.getAge());
    }

    @Test
    void testGetPatient_With_Survey_ByNameAndAge_returns_PatientDetails() {

        //given
        PatientEntity patientEntity = PatientEntity.builder().firstName("John").lastName("Ferguson").age((short) 24).build();
        PatientEntity patientSaved = testEntityManager.persistFlushFind(patientEntity);
        SurveyEntity surveyEntity = SurveyEntity.builder().patient(patientEntity).createdDate(new Date()).skinGrade((short) 6).sleepGrade((short) 8).build();
        surveyEntity.setPatient(patientEntity);
        testEntityManager.persistFlushFind(surveyEntity);

        //when
        List<PatientEntity> patientsList = patientRepository.findAllByFirstNameAndLastNameAndAge("John", "Ferguson", (short) 24);

        //then
        then(patientsList).size().isEqualTo(1);
        PatientEntity patientSearched = patientsList.get(0);
        then(patientSearched.getId()).isNotNull();
        then(patientSearched.getFirstName()).isEqualTo(patientSaved.getFirstName());
        then(patientSearched.getLastName()).isEqualTo(patientSaved.getLastName());
        then(patientSearched.getAge()).isEqualTo(patientSaved.getAge());
        then(patientSearched.getSurveys()).size().isEqualTo(1);
        then(patientSearched.getSurveys().get(0).getId()).isNotNull();
        then(patientSearched.getSurveys().get(0).getPatient().getId()).isEqualTo(patientSearched.getId());
    }

    @Test
    void test_saving_Patient_using_repository() {

        //given
        PatientEntity patientEntity = PatientEntity.builder().firstName("John").lastName("Ferguson").age((short) 24).build();
        SurveyEntity surveyEntity = SurveyEntity.builder().patient(patientEntity).createdDate(new Date()).skinGrade((short) 6).sleepGrade((short) 8).build();
        patientEntity.setSurveys(List.of(surveyEntity));
        patientRepository.save(patientEntity);
        surveyRepository.save(surveyEntity);

        //when
        List<PatientEntity> patientsList = patientRepository.findAllByFirstNameAndLastNameAndAge("John", "Ferguson", (short) 24);

        //then
        then(patientsList).size().isEqualTo(1);
        PatientEntity patientSearched = patientsList.get(0);
        then(patientSearched.getId()).isNotNull();
        then(patientSearched.getFirstName()).isEqualTo(patientEntity.getFirstName());
        then(patientSearched.getLastName()).isEqualTo(patientEntity.getLastName());
        then(patientSearched.getAge()).isEqualTo(patientEntity.getAge());
        then(patientSearched.getSurveys()).size().isEqualTo(1);
        then(patientSearched.getSurveys().get(0).getId()).isNotNull();
        then(patientSearched.getSurveys().get(0).getPatient().getId()).isEqualTo(patientSearched.getId());
    }

}