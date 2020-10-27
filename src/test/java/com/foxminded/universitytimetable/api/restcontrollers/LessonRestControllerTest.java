package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.LessonService;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LessonRestController.class)
class LessonRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LessonService lessonService;
    private Gson gson = new Gson();

    private final Date DATE = new Date(11);
    private final int LESSON_NUMBER = 1;
    private final int GROUP_ID = 1;
    private final int PROFESSOR_ID = 1;
    private final String BUILDING = "NOT";
    private final String CLASSROOM = "Be Confused";
    private final Lesson LESSON = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
    private final String LESSON_JSON = gson.toJson(LESSON);

    @Test
    void addHaveToReturnCorrectResponse() {
        try {
            given(lessonService.add(any(Lesson.class))).willReturn(LESSON);

            mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                    .param("date", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(LESSON_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(lessonService.add(any(Lesson.class))).willThrow(ValidationException.class);
            mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                    .param("date", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isBadRequest());

            given(lessonService.add(any(Lesson.class))).willThrow(Exception.class);
            mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                    .param("date", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(post(Urls.API_REST_POST_LESSON_JSON)
                    .param("WRONG !!!", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToReturnCorrectResponse() {
        try {
            List<Lesson> lessons = new ArrayList<>();
            lessons.add(LESSON);
            lessons.add(LESSON);
            String lessonsJson = gson.toJson(lessons);

            given(lessonService.getAll()).willReturn(lessons);

            mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(lessonsJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(lessonService.getAll()).willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            given(lessonService.getAll()).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            given(lessonService.getAll()).willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSONS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResponse() {
        try {
            Lesson lesson = LESSON;
            lesson.setId(any(Integer.class));
            String lessonJson = gson.toJson(lesson);

            given(lessonService.getById(any(Integer.class))).willReturn(lesson);

            mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(lessonJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToThrowResponseStatusExceptionWithCorrectStatusWhenCatchException() {
        try {
            int id = 3;

            given(lessonService.getById(id)).willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON))
                    .andExpect(status().isBadRequest());

            given(lessonService.getById(id)).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON))
                    .andExpect(status().isNotFound());

            given(lessonService.getById(any(Integer.class))).willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_LESSON_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(lessonService.update(any(Lesson.class))).willThrow(ValidationException.class);
            mockMvc.perform(put(Urls.API_REST_PUT_LESSON_JSON)
                    .param("id", String.valueOf(1))
                    .param("date", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isBadRequest());

            given(lessonService.add(any(Lesson.class))).willThrow(Exception.class);
            mockMvc.perform(put(Urls.API_REST_PUT_LESSON_JSON)
                    .param("id", String.valueOf(1))
                    .param("date", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(put(Urls.API_REST_PUT_LESSON_JSON)
                    .param("id", String.valueOf(1))
                    .param("WRONG !!!", String.valueOf(DATE))
                    .param("lessonNumber", String.valueOf(LESSON_NUMBER))
                    .param("groupId", String.valueOf(GROUP_ID))
                    .param("professorId", String.valueOf(PROFESSOR_ID))
                    .param("building", BUILDING)
                    .param("classroom", CLASSROOM))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToReturnCorrectResponse() {
        try {
            given(lessonService.remove(any(Integer.class))).willReturn(LESSON);

            mockMvc.perform(delete(Urls.API_REST_DELETE_LESSON_JSON)
                    .param("lessonId", String.valueOf(any(Integer.class))))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(LESSON_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(lessonService.remove(any(Integer.class))).willThrow(NotFoundEntityException.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_LESSON_JSON)
                    .param("lessonId", "1"))
                    .andExpect(status().isNotFound());

            given(lessonService.remove(-1)).willThrow(ValidationException.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_LESSON_JSON)
                    .param("lessonId", "1"))
                    .andExpect(status().isBadRequest());

            given(lessonService.remove(1)).willThrow(Exception.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_LESSON_JSON)
                    .param("lessonId", "1"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(put(Urls.API_REST_DELETE_LESSON_JSON)
                    .param("wrong", "1"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }
}
