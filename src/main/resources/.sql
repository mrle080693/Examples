drop database university_timetable;

drop table if exists professors;
drop table if exists groups;
drop table if exists lessons;

create database university_timetable;

create table professors
(
  id         serial primary key,
  name       char(100),
  surname    char(100),
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
  date         date,
  lesson_number integer,
  group_id      integer,
  professor_id  integer,
  building     char(100),
  classroom    char(100)
);
