package com.foxminded.universitytimetable.dao.impl.queries;

public class Queries {
    // groups table queries
    public static final String ADD_GROUP_QUERY = "insert into groups (name) values(?)";
    public static final String GET_ALL_GROUPS_QUERY = "select id, name from groups";
    public static final String GET_GROUP_BY_ID_QUERY = "select id, name from groups where id = ?";
    public static final String GET_GROUP_BY_NAME_QUERY = "select id, name from groups where name = ?";
    public static final String UPDATE_GROUP_QUERY = "update groups set name = ? where id = ?";
    public static final String REMOVE_GROUP_QUERY = "delete from groups where id = ?";

    // Queries for TimetableImpl class but for table lessons
    public static final String GET_GROUP_TIMETABLE_QUERY =
            "select * from lessons where groupId = ? and date = date between date(?) and date(?)";
    public static final String GET_PROFESSOR_TIMETABLE_QUERY =
            "select * from lessons where professorId = ? and date = date between date(?) and date(?)";

    // lessons table queries
    public static final String ADD_LESSON_QUERY = "insert into lessons (date, lessonNumber, groupId, professorId, " +
            "building, classroom) values(?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_LESSONS_QUERY = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons";
    public static final String GET_LESSON_BY_ID_QUERY = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons where id = ?";
    public static final String UPDATE_LESSON_QUERY = "update lessons set (date, lessonNumber, groupId, " +
            "professorId, building, classroom) values(?,?,?,?,?,?) where id = ?";
    public static final String REMOVE_LESSON_QUERY = "delete from lessons where id = ?";

    // professors table queries
    public static final String ADD_PROFESSOR_QUERY = "insert into professors (name, surName, patronymic, subject) " +
            "values(?, ?, ?, ?)";
    public static final String GET_ALL_PROFESSORS_QUERY = "select id, name, surName, patronymic, subject from professors";
    public static final String GET_PROFESSOR_BY_ID_QUERY = "select id, name, surName, patronymic, subject " +
            "from professors where id = ?";
    public static final String GET_PROFESSOR_BY_SURNAME_QUERY = "select id, name from professors where surName = ?";
    public static final String UPDATE_PROFESSOR_QUERY = "update professors set (name) where id = ?";
    public static final String REMOVE_PROFESSOR_QUERY = "delete from professors where id = ?";

    // StatisticImpl class queries
    public static final String GET_PROFESSOR_EMPLOYMENT = "select count(professorId) from lessons where professorId = ?" +
            "and date = date between date(?) and date(?)";
    public static final String GET_GROUP_EMPLOYMENT = "select count(groupId) from lessons where groupId = ?" +
            "and date = date between date(?) and date(?)";
}
