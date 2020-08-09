package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.MvcWebConfig;
import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
class StatisticsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LessonImpl lessonImpl;

    @Autowired
    private GroupImpl groupImpl;

    @Autowired
    private ProfessorImpl professorImpl;

    private Lesson lesson;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getStatisticsPageHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk());
    }

    @Test
    void getStatisticsPageHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/h"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStatisticsPageHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(view().name("statistics"));
    }

    @Test
    void getGroupEmploymentHaveToReturnOkStatus() throws Exception {
        Group group = new Group("Test");
        Professor professor = new Professor("Test", "Test", "Test", "Test");
        int groupId = groupImpl.add(group);
        int professorId = professorImpl.add(professor);

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1,
                groupId, professorId, "Building", "Classroom");

        lessonImpl.add(lesson);

        Date from = new Date(Calendar.getInstance().getTime().getTime());
        Date till = new Date(Calendar.getInstance().getTime().getTime());

        mockMvc.perform(get("/statistics/getGroupEmployment")
                .param("id", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }

    @Test
    void getProfessorEmploymentHaveToReturnOkStatus() throws Exception {
        Group group = new Group("Test");
        Professor professor = new Professor("Test", "Test", "Test", "Test");
        int groupId = groupImpl.add(group);
        int professorId = professorImpl.add(professor);

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1,
                groupId, professorId, "Building", "Classroom");

        lessonImpl.add(lesson);

        Date from = new Date(Calendar.getInstance().getTime().getTime());
        Date till = new Date(Calendar.getInstance().getTime().getTime());

        mockMvc.perform(get("/statistics/getProfessorEmployment")
                .param("id", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }
}
