package com.foxminded.universitytimetable.dao.queries;

public class JPQLQueries {
    // groups table queries
    public static String GET_ALL_GROUPS = "select e from Group e";
    public static String GET_GROUP_BY_ID = "select e from Group e where e.id = :id";
    public static String GET_GROUPS_BY_NAME = "select e from Group e where e.name = :name";
    public static final String DELETE_GROUP = "delete from Group e where e.id = :id";

    // lessons table queries
    public static final String GET_ALL_LESSONS = "select e from Lesson e";
    public static final String GET_LESSON_BY_ID = "select e from Lesson e where e.id = :id";
    public static final String DELETE_LESSON = "delete from Lesson e where e.id = :id";

    // professors table queries
    public static final String GET_ALL_PROFESSORS = "select e from Professor e";
    public static final String GET_PROFESSOR_BY_ID = "select e from Professor e where e.id = :id";
    public static final String GET_PROFESSORS_BY_SURNAME = "select e from Professor e where e.surname = :surname";
    public static final String DELETE_PROFESSOR = "delete from Professor e where e.id = :id";

    // StatisticImplHibernate class queries
    public static final String GET_PROFESSOR_EMPLOYMENT = "select count(*) from Lesson e " +
            "where e.professorId = :professorId and e.date >= :from and e.date <= :till";
    public static final String GET_GROUP_EMPLOYMENT = "select count(*) from Lesson e " +
            "where e.groupId = :groupId and e.date >= :from and e.date <= :till";

    // TimetableImplHibernate class queries
    public static final String GET_GROUP_TIMETABLE = "select e from Lesson e " +
            "where e.groupId = :groupId and e.date >= :from and e.date <= :till";
    public static final String GET_PROFESSOR_TIMETABLE = "select e from Lesson e " +
            "where e.professorId = :professorId and e.date >= :from and e.date <= :till";
}
