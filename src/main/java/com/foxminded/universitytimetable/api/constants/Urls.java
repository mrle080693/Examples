package com.foxminded.universitytimetable.api.constants;

public interface Urls {
    String API_GET_INDEX_HTML = "/api/get/index/html";
    String API_GET_GROUPS_HTML = "/api/get/groups/html";
    String API_GET_PROFESSORS_HTML = "/api/get/professors/html";
    String API_GET_LESSONS_HTML = "/api/get/lessons/html";
    String API_GET_STATISTICS_HTML = "/api/get/statistics/html";
    String API_GET_TIMETABLE_HTML = "/api/get/timetable/html";
    String API_GET_ERROR_HTML = "/api/get/sorry/html";

    String API_REST_GET_GROUP_JSON_BY_ID = "/api/rest/get/group/json/{id}";
    String API_REST_GET_GROUP_JSON_BY_NAME = "/api/rest/get/group/json/{name}";
    String API_REST_GET_GROUPS_JSON = "/api/rest/get/groups/json";
    String API_REST_GET_PROFESSOR_JSON_BY_ID = "/api/rest/get/professor/json/{id}";
    String API_REST_GET_PROFESSOR_JSON_BY_SURNAME = "/api/rest/get/group/json/{surname}";
    String API_REST_GET_PROFESSORS_JSON = "/api/rest/get/professors/json";
    String API_REST_GET_LESSON_JSON = "/api/rest/get/lesson/json/{id}";
    String API_REST_GET_LESSONS_JSON = "/api/rest/get/lessons/json";
    String API_REST_GET_GROUP_EMPLOYMENT_JSON = "/api/rest/get/group-employment";
    String API_REST_GET_PROFESSOR_EMPLOYMENT_JSON = "/api/rest/get/professor-employment";
    String API_REST_GET_GROUP_TIMETABLE_JSON = "/api/rest/get/group-timetable";
    String API_REST_GET_PROFESSOR_TIMETABLE_JSON = "/api/rest/get/professor-timetable";

    String API_REST_POST_GROUP_JSON = "/api/rest/post/group/json";
    String API_REST_POST_PROFESSOR_JSON = "/api/rest/post/professor/json";
    String API_REST_POST_LESSON_JSON = "/api/rest/post/lesson/json";

    String API_REST_PUT_GROUP_JSON = "/api/rest/put/group/json";
    String API_REST_PUT_PROFESSOR_JSON = "/api/rest/put/professor/json";
    String API_REST_PUT_LESSON_JSON = "/api/rest/put/lesson/json";

    String API_REST_DELETE_GROUP_JSON = "/api/rest/delete/group/json";
    String API_REST_DELETE_PROFESSOR_JSON = "/api/rest/delete/professor/json";
    String API_REST_DELETE_LESSON_JSON = "/api/rest/delete/lesson/json";
}
