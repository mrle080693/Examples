drop database if exists universityTimetable;
drop table if exists dayTimetable;
drop table if exists professors;
drop table if exists groups;
drop table if exists lessons;

create database universityTimetable;

create table dayTimetable
(
  id   serial primary key,
  date date not null
);

create table professors
(
  id         serial primary key,
  name       char(100),
  surName    char(100),
  patronymic char(100),
  subject    char(100)
);

create table groups
(
  id   serial primary key,
  name char(100)
);

create table lessons
(
  id           serial primary key,
  lessonNumber integer,
  groupId      integer,
  professorId  integer,
  building     char(100),
  classroom    char(100),
  foreign key (groupId) references groups (id) on delete cascade,
  foreign key (professorId) references professors (id) on delete set null
)
