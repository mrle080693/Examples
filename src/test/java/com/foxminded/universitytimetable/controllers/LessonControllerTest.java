package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.models.Lesson;
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
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
// Some tests may be single running
class LessonControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private Lesson lesson;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1,
                1, 1, "Building", "Classroom");
    }

    @Test
    void addHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addHaveToReturnRedirectToLessons() throws Exception {
        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()))
                .andExpect(redirectedUrl("/lessons"));
    }

    @Test
    void addHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/lessons/add")
                .param("wrong", "u"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void lessonsHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/lessons/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/lessons"))
                .andExpect(view().name("lessons"));
    }

    @Test
    void getAllHaveToReturnCorrectModel() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(post("/professors/add")
                .param("newName", "Test")
                .param("newSurname", "Test")
                .param("newPatronymic", "Test")
                .param("newSubject", "Test"));

        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()));

        mockMvc.perform(get("/lessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(model().size(1));
    }

    @Test
    void getByIdHaveToReturnOkStatusIfTableLessonsHaveInputId() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(post("/professors/add")
                .param("newName", "Test")
                .param("newSurname", "Test")
                .param("newPatronymic", "Test")
                .param("newSubject", "Test"));

        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()));

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
        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(view().name("lessons"));
    }

    // Single running test
    @Test
    void getByIdHaveToReturnCorrectModelParameters() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(post("/professors/add")
                .param("newName", "Test")
                .param("newSurname", "Test")
                .param("newPatronymic", "Test")
                .param("newSubject", "Test"));

        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()));

        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("date"))
                .andExpect(model().attributeExists("lessonNumber"))
                .andExpect(model().attributeExists("groupId"))
                .andExpect(model().attributeExists("professorId"))
                .andExpect(model().attributeExists("building"))
                .andExpect(model().attributeExists("classroom"))

                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("lessonNumber", lesson.getLessonNumber()))
                .andExpect(model().attribute("groupId", lesson.getGroupId()))
                .andExpect(model().attribute("professorId", lesson.getProfessorId()))
                .andExpect(model().attribute("building", lesson.getBuilding()))
                .andExpect(model().attribute("classroom", lesson.getClassroom()));
    }

    // Single running test
    @Test
    void updateHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/lessons/update")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()))
                .andExpect(status().is3xxRedirection());
    }

    // Single running test
    @Test
    void updateHaveToRedirectToLessonsPage() throws Exception {
        mockMvc.perform(post("/lessons/update")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()))
                .andExpect(redirectedUrl("/lessons"));
    }

    @Test
    void updateHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/lessons/update")
                .param("wrong", "1"))
                .andExpect(status().is4xxClientError());
    }

    // Single running test
    @Test
    void updateHaveToReturnRedirectionStatusIfUpdatedLessonDoesNotExist() throws Exception {
        mockMvc.perform(post("/lessons/update")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/lessons/remove")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectToLessons() throws Exception {
        mockMvc.perform(post("/lessons/remove")
                .param("id", "1"))
                .andExpect(redirectedUrl("/lessons"));
    }

    @Test
    void removeHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/lessons/remove")
                .param("wrong", "upddfsgs"))
                .andExpect(status().is4xxClientError());
    }

    // Single running test
    @Test
    void removeHaveToRemoveLessonWithInputId() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(post("/professors/add")
                .param("newName", "Test")
                .param("newSurname", "Test")
                .param("newPatronymic", "Test")
                .param("newSubject", "Test"));

        mockMvc.perform(post("/lessons/add")
                .param("date", String.valueOf(lesson.getDate()))
                .param("lessonNumber", String.valueOf(lesson.getLessonNumber()))
                .param("groupId", String.valueOf(lesson.getGroupId()))
                .param("professorId", String.valueOf(lesson.getProfessorId()))
                .param("building", lesson.getBuilding())
                .param("classroom", lesson.getClassroom()));

        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/lessons/remove")
                .param("id", "1"));

        mockMvc.perform(get("/lessons/getById/{id}", 1))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void removeHaveToReturnRedirectionStatusIfRemovedLessonDoesNotExist() throws Exception {
        mockMvc.perform(post("/lessons/remove")
                .param("id", "190000000"))
                .andExpect(status().is3xxRedirection());
    }
}
