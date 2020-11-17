package com.foxminded.universitytimetable.api.restcontrollers.system;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.models.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StatisticsRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LessonDAO lessonDAO;

    private final Date FROM = new Date(Calendar.getInstance().getTime().getTime() - 999999999);
    private final Date TILL = new Date(Calendar.getInstance().getTime().getTime() + 999999999);
    private Lesson lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1, 1,
            1, "Any", "Any");

    @Test
    void getGroupEmployment_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        String expected = "0";
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                .param("groupId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        lessonDAO.add(lesson);
        expected = "1";
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                .param("groupId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void getGroupEmployment_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                .param("wrong", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                .param("groupId", "wrong")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProfessorEmployment_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        String expected = "0";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                .param("professorId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        lessonDAO.add(lesson);
        expected = "1";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                .param("professorId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void getProfessorEmployment_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                .param("wrong", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                .param("professorId", "wrong")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());
    }
}
