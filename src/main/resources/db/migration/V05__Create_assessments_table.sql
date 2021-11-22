create table ASSESSMENTS
(
    ID                 bigint auto_increment,
    PATIENT_ID         bigint not null,
    ASSESSMENT_TYPE_ID bigint not null,
    constraint ASSESSMENTS_ID_uindex
        unique (ID),
    constraint FK__ASSESSMENTS_TO_ASSESSMENT_TYPES
        foreign key (ASSESSMENT_TYPE_ID) references ASSESSMENT_TYPES (ID),
    constraint FK__ASSESSMENTS_TO_PATIENTS
        foreign key (PATIENT_ID) references PATIENTS (ID)
);