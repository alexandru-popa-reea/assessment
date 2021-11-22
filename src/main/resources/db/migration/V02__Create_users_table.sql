create table USERS
(
    ID         bigint auto_increment,
    USERNAME   varchar(30)  not null,
    FIRST_NAME varchar(50)  not null,
    LAST_NAME  varchar(75)  not null,
    EMAIL      varchar(100) not null,
    PATIENT_ID bigint       null,
    PASSWORD   varchar(300) null,
    constraint USER_ID_uindex
        unique (ID),
    constraint USER_PATIENT_ID_uindex
        unique (PATIENT_ID),
    constraint USER_USERNAME_uindex
        unique (USERNAME),
    constraint FK__USERS_TO_PATIENTS
        foreign key (PATIENT_ID) references PATIENTS (ID)
);

alter table USERS
    add primary key (ID);