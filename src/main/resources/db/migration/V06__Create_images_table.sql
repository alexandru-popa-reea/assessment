create table IMAGES
(
    ID            bigint auto_increment,
    IMG_PATH      varchar(500) not null,
    CREATED_DATE  datetime     not null,
    ASSESSMENT_ID bigint       not null,
    constraint IMAGES_ID_uindex
        unique (ID),
    constraint FK__IMAGES_TO_ASSESSMENTS
        foreign key (ASSESSMENT_ID) references ASSESSMENTS (ID)
);

alter table IMAGES
    add primary key (ID);