package com.foxminded.universitytimetable.api.constants;

public interface Urls {
    String GET_INDEX_HTML = "/api/get/index/html";
    String GET_GROUPS_HTML = "/api/get/groups/html";
    String GET_PROFESSORS_HTML = "/api/get/professors/html";
    String GET_LESSONS_HTML = "/api/get/lessons/html";
    String GET_STATISTICS_HTML = "/api/get/statistics/html";
    String GET_TIMETABLE_HTML = "/api/get/timetable/html";
    String GET_ERROR_HTML = "/api/get/sorry/html";

    String GET_GROUP_JSON_BY_ID = "/api/get/group/json/{id}";
    String GET_GROUP_JSON_BY_NAME = "/api/get/group/json/{name}";
    String GET_GROUPS_JSON = "/api/get/groups/json";
    String GET_PROFESSOR_JSON_BY_ID = "/api/get/professor/json/{id}";
    String GET_PROFESSOR_JSON_BY_SURNAME = "/api/get/group/json/{surname}";
    String GET_PROFESSORS_JSON = "/api/get/professors/json";
    String GET_LESSON_JSON = "/api/get/lesson/json/{id}";
    String GET_LESSONS_JSON = "/api/get/lessons/json";
    String GET_EMPLOYMENT_GROUP_JSON = "/api/get/employment/group";
    String GET_EMPLOYMENT_PROFESSOR_JSON = "/api/get/employment/professor";
    String GET_TIMETABLE_GROUP_JSON = "/api/get/timetable/group";
    String GET_TIMETABLE_PROFESSOR_JSON = "/api/get/timetable/professor";

    String POST_GROUP_JSON = "/api/post/group/json";
    String POST_PROFESSOR_JSON = "/api/post/professor/json";
    String POST_LESSON_JSON = "/api/post/lesson/json";

    String PUT_GROUP_JSON = "/api/put/group/json";
    String PUT_PROFESSOR_JSON = "/api/put/professor/json";
    String PUT_LESSON_JSON = "/api/put/lesson/json";

    String DELETE_GROUP_JSON = "/api/delete/group/json";
    String DELETE_PROFESSOR_JSON = "/api/delete/professor/json";
    String DELETE_LESSON_JSON = "/api/delete/lesson/json";
}
