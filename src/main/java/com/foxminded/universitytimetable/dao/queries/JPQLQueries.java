package com.foxminded.universitytimetable.dao.queries;

public class JPQLQueries {
    // groups table queries
    public static String GET_ALL_GROUPS = "select e from Group e";
    public static String GET_GROUP_BY_ID = "select e from Group e where e.id = :id";
    public static String GET_GROUPS_BY_NAME = "select e from Group e where e.name = :name";
    public static final String DELETE_GROUP = "delete from Group e where e.id = :id";

    // lessons table queries
    public static final String GET_ALL_LESSONS = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons";
    public static final String GET_LESSON_BY_ID = "select id, date, lessonNumber, groupId, professorId," +
            "building, classroom from lessons where id = ?";
    public static final String DELETE_LESSON = "delete from lessons where id = ?";

    // professors table queries
    public static final String GET_ALL_PROFESSORS = "select id, name, surName, patronymic, subject from professors";
    public static final String GET_PROFESSOR_BY_ID = "select id, name, surName, patronymic, subject " +
            "from professors where id = ?";
    public static final String GET_PROFESSORS_BY_SURNAME = "select id, name, surName, patronymic, subject " +
            "from professors where surName = ?";
    public static final String DELETE_PROFESSOR = "delete from professors where id = ?";

    // StatisticImpl class queries
    public static final String GET_PROFESSOR_EMPLOYMENT = "select count(*) from lessons where professorId = ? and date >= ? " +
            "and date <= ?";
    public static final String GET_GROUP_EMPLOYMENT = "select count(*) from lessons where groupId = ? and date >= ? " +
            "and date <= ?";

    // SQLQueries for TimetableImpl class but for table lessons
    public static final String GET_GROUP_TIMETABLE_QUERY =
            "select * from lessons where groupId = ? and date >= ? and date <= ?";
    public static final String GET_PROFESSOR_TIMETABLE_QUERY =
            "select * from lessons where professorId = ? and date >= ? and date <= ?";

}
