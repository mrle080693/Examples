package com.foxminded.universitytimetable.api.controllers;

import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
// Some tests may be single running
class ProfessorControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private Professor professor = new Professor("Test", "Test", "Test", "Test");

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addHaveToReturnRedirectToProfessors() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()))
                .andExpect(redirectedUrl("/professors"));
    }

    @Test
    void addHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("wrong", "u"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void professorHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/professors/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/professors"))
                .andExpect(status().isOk());
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/professors"))
                .andExpect(view().name("professors"));
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnCorrectModel() throws Exception {
        mockMvc.perform(get("/professors"))
                .andExpect(model().attributeExists("professors"))
                .andExpect(model().size(1));
    }

    // Single running test
    @Test
    void getByIdHaveToReturnOkStatusIfTableGroupsHaveInputId() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdHaveToReturnIsBadRequestStatusIfTableGroupsHaveNotInputId() throws Exception {
        mockMvc.perform(get("/professors/getById/{id}", 400))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(view().name("professors"));
    }

    // Single running test
    @Test
    void getByIdHaveToReturnCorrectModelParameters() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("surname"))
                .andExpect(model().attributeExists("patronymic"))
                .andExpect(model().attributeExists("subject"))

                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("name", "Test"))
                .andExpect(model().attribute("surname", "Test"))
                .andExpect(model().attribute("patronymic", "Test"))
                .andExpect(model().attribute("subject", "Test"));
    }

    @Test
    void updateHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/professors/update")
                .param("id", "1")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateHaveToRedirectToProfessors() throws Exception {
        mockMvc.perform(post("/professors/update")
                .param("id", "1")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()))
                .andExpect(redirectedUrl("/professors"));
    }

    @Test
    void updateHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/professors/update")
                .param("wrong", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateHaveToReturnRedirectionStatusIfUpdatedProfessorDoesNotExist() throws Exception {
        mockMvc.perform(post("/professors/update")
                .param("id", "1")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/professors/remove")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectToProfessorsPage() throws Exception {
        mockMvc.perform(post("/professors/remove")
                .param("id", "1"))
                .andExpect(redirectedUrl("/professors"));
    }

    @Test
    void removeHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/professors/remove")
                .param("wrong", "updatedGroup"))
                .andExpect(status().is4xxClientError());
    }

    // Single running test
    @Test
    void removeHaveToRemoveProfessorWithInputId() throws Exception {
        mockMvc.perform(post("/professors/add")
                .param("newName", professor.getName())
                .param("newSurname", professor.getSurname())
                .param("newPatronymic", professor.getPatronymic())
                .param("newSubject", professor.getSubject()));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/professors/remove")
                .param("id", "1"));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void removeHaveToReturnRedirectionStatusIfRemovedProfessorDoesNotExist() throws Exception {
        mockMvc.perform(post("/professors/remove")
                .param("id", "190000000"))
                .andExpect(status().is3xxRedirection());
    }
}
