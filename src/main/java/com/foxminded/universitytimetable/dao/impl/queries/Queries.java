package com.foxminded.universitytimetable.dao.impl.queries;

public class Queries {
    // groups table queries
    public static final String ADD_GROUP_QUERY = "insert into groups (name, surName, patronymic, subject) " +
            "values(?, ?, ?, ?)";
    public static final String GET_ALL_GROUPS_QUERY = "select id, name, surName, patronymic, subject from groups";
    public static final String GET_GROUP_BY_ID_QUERY = "select id, name from groups where id = ?";
    public static final String GET_GROUP_BY_NAME_QUERY = "select id, name from groups where name = ?";
    public static final String UPDATE_GROUP_QUERY = "update groups set name = ? where id = ?";
    public static final String REMOVE_GROUP_QUERY = "delete from groups where id = ?";
    // Возможно тут косяк
    public static final String GET_GROUP_TIMETABLE_QUERY =
            "select * from lessons where groupId = ? and date = date between date(?) and date(?)";


    // professors table queries
    public static final String GET_PROFESSOR_TIMETABLE_QUERY =
            "select * from lessons where professorId = ? and date = date between date(?) and date(?)";

    // lessons table queries
    public static final String GET_LESSONS_BY_DAY_TIMETABLE_ID_QUERY = "select * from lessons where dayTimetableId = ?";
}
