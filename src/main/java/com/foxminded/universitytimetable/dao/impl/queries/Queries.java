package com.foxminded.universitytimetable.dao.impl.queries;

public class Queries {
    // day_timetable table queries
    public static final String ADD_DAY_TIMETABLE_QUERY = "insert into day_timetable (date) values(?)";
    // Подзапрос
    public static final String GET_ALL_DAY_TIMETABLE_QUERY = "select id, date from day_timetable";
    public static final String GET_BY_ID_DAY_TIMETABLE_QUERY = "select id, date from day_timetable where id = ?";
    public static final String GET_BY_DATE_DAY_TIMETABLE_QUERY = "select id, date from day_timetable where date = ?";
    public static final String UPDATE_DAY_TIMETABLE_QUERY = "update day_timetable set date = ? where id = ?";
    public static final String REMOVE_DAY_TIMETABLE_QUERY = "delete from day_timetable where id = ?";

    // groups table queries
    public static final String ADD_GROUP_QUERY = "insert into groups (name, surName, patronymic, subject) " +
            "values(?, ?, ?, ?)";
    public static final String GET_ALL_GROUPS_QUERY = "select id, name, surName, patronymic, subject from groups";
    public static final String GET_GROUP_BY_ID_QUERY = "select id, name from groups where id = ?";
    public static final String GET_GROUP_BY_NAME_QUERY = "select id, name from groups where name = ?";





    // lessons table queries
    public static final String GET_LESSONS_BY_DAY_TIMETABLE_ID_QUERY = "select * from lessons where dayTimetableId = ?";
}
