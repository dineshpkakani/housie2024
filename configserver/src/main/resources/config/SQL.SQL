

CREATE TABLE USERS(
      id INT(11) NOT NULL AUTO_INCREMENT,
      USERNAME VARCHAR(50) NOT NULL DEFAULT '',
      PASSWORD VARCHAR(100)NOT NULL DEFAULT '',
      NAME VARCHAR(100) NOT NULL  DEFAULT '',
      EMPCODE VARCHAR(20) NOT NULL,
      DELETEFLAG INT(1) NOT NULL DEFAULT 0,
      ROLES VARCHAR(25) NOT NULL,
      CONSTRAINT PK_USERS PRIMARY KEY (id)
);
create table if not exists category
(
    uid  int          not null
    primary key,
    type varchar(255) not null
    );

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists role
(
    id   bigint       not null
    primary key,
    name varchar(255) null
    );

create table if not exists tickets
(
    uid           int          not null
    primary key,
    category      varchar(255) not null,
    currentticket varchar(255) not null,
    empcode       int          not null,
    generate_date datetime     not null,
    orgticket     varchar(255) not null,
    result        varchar(255) not null
    );

create table if not exists user
(
    id          bigint                              not null
    primary key,
    password    varchar(255)                        null,
    username    varchar(255)                        null,
    roles       varchar(12)                         not null,
    empcode     int                                 not null,
    createddate timestamp default CURRENT_TIMESTAMP not null,
    firstname   varchar(50)                         not null,
    lastname    varchar(50)                         not null
    );

CREATE TABLE player (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        email VARCHAR(150) NOT NULL UNIQUE,
        phone VARCHAR(20) NOT NULL,
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE email_otp (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) NOT NULL,
       otp VARCHAR(10) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       expires_at TIMESTAMP NOT NULL
);
alter table player
    add role varchar(20) not null;

