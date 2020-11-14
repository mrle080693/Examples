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
class TimetableRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LessonDAO lessonDAO;

    private final Date FROM = new Date(Calendar.getInstance().getTime().getTime() - 999999999);
    private final Date TILL = new Date(Calendar.getInstance().getTime().getTime() + 999999999);
    private Lesson lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1, 1,
            1, "Any", "Any");

    @Test
    void getGroupTimetableShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        String expected = "[]";
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                .param("groupId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        lessonDAO.add(lesson);
        expected = "[{\"id\":1,\"date\":\"2020-11-14\",\"lessonNumber\":1,\"groupId\":1,\"professorId\":1," +
                "\"building\":\"Any\",\"classroom\":\"Any\"}]";
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                .param("groupId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    void getGroupTimetableShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                .param("wrong", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(Urls.API_REST_GET_GROUP_TIMETABLE_JSON)
                .param("groupId", "wrong")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProfessorTimetableShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        String expected = "[]";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                .param("professorId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        lessonDAO.add(lesson);
        expected = "[{\"id\":1,\"date\":\"2020-11-14\",\"lessonNumber\":1,\"groupId\":1,\"professorId\":1," +
                "\"building\":\"Any\",\"classroom\":\"Any\"}]";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                .param("professorId", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    void getProfessorTimetableShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                .param("wrong", "1")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_TIMETABLE_JSON)
                .param("professorId", "wrong")
                .param("from", String.valueOf(FROM))
                .param("till", String.valueOf(TILL)))
                .andExpect(status().isBadRequest());
    }
}
