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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
class LessonControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private LessonImpl lessonImpl;

    @Autowired
    private GroupImpl groupImpl;

    @Autowired
    private ProfessorImpl professorImpl;

    private Lesson lesson;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Group group = new Group("Test");
        Professor professor = new Professor("Test", "Test", "Test", "Test");
        int groupId = groupImpl.add(group);
        int professorId = professorImpl.add(professor);

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1,
                groupId, professorId, "Building", "Classroom");
    }

    @Test
    void lessonsHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/lessons/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        lessonImpl.add(lesson);

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllHaveToReturnCorrectView() throws Exception {
        lessonImpl.add(lesson);

        mockMvc.perform(get("/lessons"))
                .andExpect(view().name("lessons"));
    }


    @Test
    void getAllHaveToReturnCorrectModel() throws Exception {
        lessonImpl.add(lesson);

        mockMvc.perform(get("/lessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(model().size(1));
    }

    @Test
    void getByIdHaveToReturnOkStatusIfTableGroupsHaveInputId() throws Exception {
        lessonImpl.add(lesson);

        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdHaveToReturnIsBadRequestStatusIfTableGroupsHaveNotInputId() throws Exception {
        mockMvc.perform(get("/lessons/getById/{id}", 400))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdHaveToReturnCorrectView() throws Exception {
        lessonImpl.add(lesson);

        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(view().name("lessons"));
    }
}
