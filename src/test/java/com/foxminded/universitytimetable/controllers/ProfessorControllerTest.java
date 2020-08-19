package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.MvcWebConfig;
import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
class ProfessorControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProfessorImpl professorImpl;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void professorHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/professors/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

        mockMvc.perform(get("/professors"))
                .andExpect(status().isOk());
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnCorrectView() throws Exception {
        professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

        mockMvc.perform(get("/professors"))
                .andExpect(view().name("professors"));
    }

    @Test
    void getProfessorsViewAndAllProfessorsHaveToReturnCorrectModel() throws Exception {
        professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

        mockMvc.perform(get("/professors"))
                .andExpect(model().attributeExists("professors"))
                .andExpect(model().size(1));
    }

    @Test
    void getByIdHaveToReturnOkStatusIfTableGroupsHaveInputId() throws Exception {
        professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

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
        professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

        mockMvc.perform(get("/professors/getById/{id}", 1))
                .andExpect(view().name("professors"));
    }

    @Test
    void getByIdHaveToReturnCorrectModelParameters() throws Exception {
        int id = professorImpl.add(new Professor("Test", "Test", "Test", "Test"));

        mockMvc.perform(get("/professors/getById/{id}", id))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("surname"))
                .andExpect(model().attributeExists("patronymic"))
                .andExpect(model().attributeExists("subject"))

                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("name", "Test"))
                .andExpect(model().attribute("surname", "Test"))
                .andExpect(model().attribute("patronymic", "Test"))
                .andExpect(model().attribute("subject", "Test"));
    }
}
