package com.foxminded.universitytimetable.api.restcontrollers.system;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.models.Lesson;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LessonRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LessonDAO lessonDAO;
    private Date DATE = new Date(Calendar.getInstance().getTime().getTime());
    private final int LESSON_NUMBER = 1;
    private final int GROUP_ID = 1;
    private final int PROFESSOR_ID = 1;
    private final String BUILDING = "NOT";
    private final String CLASSROOM = "Be Confused";
    private Gson gson = new Gson();

    @Test
    void addShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lesson.setId(1);
        String lessonJson = gson.toJson(lesson);

        mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                .param("date", String.valueOf(DATE))
                .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                .param("groupId", String.valueOf(GROUP_ID))
                .param("professorId", String.valueOf(PROFESSOR_ID))
                .param("building", BUILDING)
                .param("classroom", CLASSROOM))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(lessonJson));
    }

    @Test
    void addShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                .param("nam", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllShouldReturnCorrectResponse() throws Exception {
        String expected = "";
        mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON))
                .andExpect(content().string(expected));

        mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                .param("date", String.valueOf(DATE))
                .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                .param("groupId", String.valueOf(GROUP_ID))
                .param("professorId", String.valueOf(PROFESSOR_ID))
                .param("building", BUILDING)
                .param("classroom", CLASSROOM));
        mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                .param("date", String.valueOf(DATE))
                .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                .param("groupId", String.valueOf(GROUP_ID))
                .param("professorId", String.valueOf(PROFESSOR_ID))
                .param("building", BUILDING)
                .param("classroom", CLASSROOM));

        expected = "[{\"id\":1,\"date\":\"2020-11-14\",\"lessonNumber\":1,\"groupId\":1,\"professorId\":1," +
                "\"building\":\"NOT\",\"classroom\":\"Be Confused\"},{\"id\":2,\"date\":\"2020-11-14\"," +
                "\"lessonNumber\":1,\"groupId\":1,\"professorId\":1,\"building\":\"NOT\",\"classroom\":\"Be Confused\"}]";
        mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON))
                .andExpect(content().string(expected));
    }

    @Test
    void getByIdShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);
        lesson.setId(1);
        String lessonJson = gson.toJson(lesson);

        mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(lessonJson));
    }

    @Test
    void getByIdShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON, "wrong"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);
        lesson.setId(1);
        lesson.setClassroom("updated");
        String lessonJson = gson.toJson(lesson);

        mockMvc.perform(put(Urls.API_REST_PUT_LESSON_JSON)
                .param("id", "1")
                .param("date", String.valueOf(DATE))
                .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                .param("groupId", String.valueOf(GROUP_ID))
                .param("professorId", String.valueOf(PROFESSOR_ID))
                .param("building", BUILDING)
                .param("classroom", lesson.getClassroom()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(lessonJson));
    }

    @Test
    void updateShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(put(Urls.API_REST_PUT_LESSON_JSON)
                .param("id", "wrong !!")
                .param("date", String.valueOf(DATE))
                .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                .param("groupId", String.valueOf(GROUP_ID))
                .param("professorId", String.valueOf(PROFESSOR_ID))
                .param("building", BUILDING)
                .param("classroom", CLASSROOM))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                .param("id", "wrong")
                .param("name", "name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);

        mockMvc.perform(delete(Urls.API_REST_DELETE_LESSON_JSON)
                .param("lessonId", "1"))
                //.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));
    }

    @Test
    void removeShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                .param("wrong", "1"))
                .andExpect(status().isBadRequest());
    }
}
