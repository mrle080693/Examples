package com.foxminded.universitytimetable.dao.queries;

public class SQLQueries {
    // groups table queries
    public static final String ADD_GROUP = "insert into groups (name) values(?)";
    public static final String GET_ALL_GROUPS = "select id, name from groups";
    public static final String GET_GROUP_BY_ID = "select id, name from groups where id = ?";
    public static final String GET_GROUPS_BY_NAME = "select id, name from groups where name = ?";
    public static final String UPDATE_GROUP = "update groups set name = ? where id = ?";
    public static final String DELETE_GROUP = "delete from groups where id = ?";

    // lessons table queries
    public static final String ADD_LESSON = "insert into lessons (date, lessonNumber, groupId, professorId, " +
            "building, classroom) values(?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_LESSONS = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons";
    public static final String GET_LESSON_BY_ID = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons where id = ?";
    public static final String UPDATE_LESSON = "update lessons set date = ?, lessonNumber = ?, groupId = ?, " +
            "professorId = ?, building = ?, classroom = ? where id = ?";
    public static final String DELETE_LESSON = "delete from lessons where id = ?";

    // professors table queries
    public static final String ADD_PROFESSOR = "insert into professors (name, surName, patronymic, subject) " +
            "values(?, ?, ?, ?)";
    public static final String GET_ALL_PROFESSORS = "select id, name, surName, patronymic, subject from professors";
    public static final String GET_PROFESSOR_BY_ID = "select id, name, surName, patronymic, subject " +
            "from professors where id = ?";
    public static final String GET_PROFESSORS_BY_SURNAME = "select id, name, surName, patronymic, subject " +
            "from professors where surName = ?";
    public static final String UPDATE_PROFESSOR = "update professors set name = ?, surName = ?, patronymic = ?, " +
            "subject = ? where id = ?";
    public static final String DELETE_PROFESSOR = "delete from professors where id = ?";

    // StatisticImpl class queries
    public static final String GET_PROFESSOR_EMPLOYMENT = "select count(*) from lessons where professorId = ? and date >= ? " +
            "and date <= ?";
    public static final String GET_GROUP_EMPLOYMENT = "select count(*) from lessons where groupId = ? and date >= ? " +
            "and date <= ?";

    // SQLQueries for TimetableImpl class but for table lessons
    public static final String GET_GROUP_TIMETABLE =
            "select * from lessons where groupId = ? and date >= ? and date <= ?";
    public static final String GET_PROFESSOR_TIMETABLE =
            "select * from lessons where professorId = ? and date >= ? and date <= ?";
}
