drop database if exists university_timetable;
drop table if exists day_timetable cascade;
drop table if exists professors cascade;
drop table if exists groups cascade;
drop table if exists lessons cascade;

CREATE DATABASE university_timetable;

CREATE TABLE day_timetable
(
  id   serial primary key,
  date date
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
  id             serial primary key,
  lessonNumber   integer,
  groupId        integer,
  professorId    integer,
  dayTimetableId integer,
  building       char(100),
  classroom      char(100),
  foreign key (dayTimetableId) references day_timetable (id) on delete cascade,
  foreign key (groupId) references groups (id) on delete cascade,
  foreign key (professorId) references professors (id) on delete set null
);

create table lessons_days
(
  lessonId       integer,
  dayTimetableId integer,
  foreign key (lessonId) references lessons (id) on delete cascade
);