create table REL_USERS_TO_PATIENTS
(
    USER_ID    bigint not null,
    PATIENT_ID bigint not null,
    constraint FK__REL_USERS_TO_PATIENTS__TO_PATIENTS
        foreign key (PATIENT_ID) references PATIENTS (ID),
    constraint FK__REL_USERS_TO_PATIENTS__TO_USERS
        foreign key (USER_ID) references USERS (ID)
);