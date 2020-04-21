package com.foxminded.universitytimetable.configurations;

import org.springframework.jdbc.core.JdbcTemplate;

public class PostgreSQLInitializer {
    private SpringJDBCPostgreSQLConfig config = new SpringJDBCPostgreSQLConfig();
    private JdbcTemplate jdbcTemplate = config.postgreSQLJdbcTemplate();

    private final String DROP_DB = "drop database if exists universityTimetable";
    private final String CREATE_DB = "create database universityTimetable";

    private final String DROP_DAY_TIMETABLES = "drop table if exists dayTimetable";
    private final String CREATE_DAY_TIMETABLES = "create table dayTimetable ("
            + "id serial primary key, " +
            "date date not null)";

    private final String DROP_PROFESSORS = "drop table if exists professors";
    private final String CREATE_PROFESSORS = "create table professors ("
            + "id serial primary key, " +
            "name char(100)," +
            "surName char(100)," +
            "patronymic char(100)," +
            "subject char(100)" +
            ")";

    private final String DROP_GROUPS = "drop table if exists groups";
    private final String CREATE_GROUPS = "create table groups (" +
            "id serial primary key, " +
            "name char(100))";

    private final String DROP_LESSONS = "drop table if exists lessons";
    private final String CREATE_LESSONS = "create table lessons ("
            + "id serial primary key, " +
            "lessonNumber integer," +
            "groupId integer," +
            "professorId integer," +
            "building char(100)," +
            "classroom char(100)," +
            "foreign key (groupId) references groups (id) on delete cascade," +             // ТУТ!!!
            "foreign key (professorId) references professors (id) on delete set null" +
            ")";

    public void init() {
        query(DROP_DB);
        query(CREATE_DB);

        // Tables
        query(DROP_DAY_TIMETABLES);
        query(CREATE_DAY_TIMETABLES);

        query(DROP_PROFESSORS);
        query(CREATE_PROFESSORS);

        query(DROP_GROUPS);
        query(CREATE_GROUPS);

        query(DROP_LESSONS);
        query(CREATE_LESSONS);
    }

    private void query(String table) {
        jdbcTemplate.execute(table);
    }
}
