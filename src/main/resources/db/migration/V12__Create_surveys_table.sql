create table SURVEYS
(
    ID           bigint auto_increment,
    PATIENT_ID   bigint   not null,
    CREATED_DATE datetime not null,
    SLEEP_GRADE  smallint not null,
    SKIN_GRADE   smallint not null,
    constraint SURVEYS_ID_uindex
        unique (ID)
);

alter table SURVEYS
    add primary key (ID);