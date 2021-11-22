create table PATIENTS
(
    ID         bigint auto_increment,
    FIRST_NAME varchar(50) not null,
    LAST_NAME  varchar(75) not null,
    AGE        int         not null,
    constraint PATIENT_ID_uindex
        unique (ID)
);

alter table PATIENTS
    add primary key (ID);



