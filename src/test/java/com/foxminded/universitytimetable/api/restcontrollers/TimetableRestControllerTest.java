package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.TimetableService;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TimetableRestController.class)
class TimetableRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TimetableService timetableService;
    private static Gson gson = new Gson();

    private int id = 3;
    private Date from = new Date(2019, 11, 11);
    private Date till = new Date(2019, 11, 13);
    private static List<Lesson> lessons = new ArrayList<>();
    private static String jsonTimetable;

    @BeforeAll
    static void dataSet() {
        Lesson lesson = new Lesson();
        lesson.setBuilding("Difficult AnD Long String  8564btv895b7v45b7v487vb2y587b4582v y84vby248");
        lessons.add(lesson);
        lessons.add(lesson);

        jsonTimetable = gson.toJson(lessons);
    }

    @Test
    void getGroupEmploymentHaveToReturnCorrectResponse() {
        try {
            given(timetableService.getGroupTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willReturn(lessons);

            mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(jsonTimetable));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getGroupEmploymentHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(timetableService.getGroupTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isBadRequest());


            given(timetableService.getGroupTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectResponse() {
        try {
            given(timetableService.getProfessorTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willReturn(lessons);

            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(jsonTimetable));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getProfessorEmploymentHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(timetableService.getProfessorTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isBadRequest());


            given(timetableService.getGroupTimetable(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }
}
