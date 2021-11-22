create table ASSESSMENT_TYPES
(
    ID   bigint auto_increment,
    NAME varchar(30) not null,
    constraint ASSESSMENT_TYPES_ID_uindex
        unique (ID)
);

alter table ASSESSMENT_TYPES
    add primary key (ID);