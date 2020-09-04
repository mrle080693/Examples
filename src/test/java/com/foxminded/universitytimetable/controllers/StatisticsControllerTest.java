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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private MockMvc mockMvc;

    private Lesson lesson;
    private Group group;
    private Professor professor;
    private int groupId;
    private int professorId;
    private Date from;
    private Date till;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        group = new Group("Test");
        professor = new Professor("Test", "Test", "Test", "Test");
        groupId = groupImpl.add(group);
        professorId = professorImpl.add(professor);

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1,
                groupId, professorId, "Building", "Classroom");

        lessonImpl.add(lesson);

        from = new Date(Calendar.getInstance().getTime().getTime());
        till = new Date(Calendar.getInstance().getTime().getTime());
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
        mockMvc.perform(get("/statistics/get_group_employment")
                .param("groupId", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }

    @Test
    void getGroupEmploymentHaveToReturnCorrectContentType() throws Exception {
        mockMvc.perform(get("/statistics/get_group_employment")
                .param("groupId", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print()).andReturn();
    }

    @Test
    void getGroupEmploymentHaveToReturnCorrectResult() throws Exception {
        mockMvc.perform(get("/statistics/get_group_employment")
                .param("groupId", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().string("1"));
    }

    @Test
    void getProfessorEmploymentHaveToReturnOkStatus() throws Exception {
        mockMvc.perform(get("/statistics/get_professor_employment")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectContentType() throws Exception {
        mockMvc.perform(get("/statistics/get_professor_employment")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print()).andReturn();
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectResult() throws Exception {
        mockMvc.perform(get("/statistics/get_professor_employment")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().string("1"));
    }
}
