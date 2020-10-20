package com.foxminded.universitytimetable.api.controllers;

import com.foxminded.universitytimetable.database.impl.GroupImpl;
import com.foxminded.universitytimetable.database.impl.LessonImpl;
import com.foxminded.universitytimetable.database.impl.ProfessorImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class TimetableControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private LessonImpl lessonImpl;
    @Autowired
    private GroupImpl groupImpl;
    @Autowired
    private ProfessorImpl professorImpl;

    private MockMvc mockMvc;
    private Gson gson;

    private Lesson lesson;
    private Group group;
    private Professor professor;
    private Group groupId;
    private Professor professorId;
    private Date from;
    private Date till;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();

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
    void getTimetablePageHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/timetable"))
                .andExpect(status().isOk());
    }

    @Test
    void getTimetablePageHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/h"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTimetablePageHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/timetable"))
                .andExpect(view().name("timetable"));
    }

    @Test
    void getGroupTimetableHaveToReturnOkStatus() throws Exception {
        mockMvc.perform(get("/timetable/get_group_timetable")
                .param("groupId", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }

    @Test
    void getGroupTimetableHaveToReturnCorrectContentType() throws Exception {
        mockMvc.perform(get("/timetable/get_group_timetable")
                .param("groupId", String.valueOf(groupId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print()).andReturn();
    }

    @Test
    void getGroupTimetableHaveToReturnCorrectResult() throws Exception {
        lesson.setId(7);
        List<Lesson> expected = new ArrayList<>();
        expected.add(lesson);

        mockMvc.perform(get("/timetable/get_group_timetable")
                .param("groupId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().string(gson.toJson(expected)));
    }

    @Test
    void getProfessorEmploymentHaveToReturnOkStatus() throws Exception {
        mockMvc.perform(get("/timetable/get_professor_timetable")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(status().isOk());
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectContentType() throws Exception {
        mockMvc.perform(get("/timetable/get_professor_timetable")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print()).andReturn();
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectResult() throws Exception {
        lesson.setId(3);
        List<Lesson> expected = new ArrayList<>();
        expected.add(lesson);

        mockMvc.perform(get("/timetable/get_professor_timetable")
                .param("professorId", String.valueOf(professorId))
                .param("from", String.valueOf(from))
                .param("till", String.valueOf(till)))
                .andExpect(content().string(gson.toJson(expected)));
    }
}
