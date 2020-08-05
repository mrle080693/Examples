package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.MvcWebConfig;
import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.models.Group;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
class IndexControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void indexHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void indexHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/h"))
                .andExpect(status().isNotFound());
    }

    @Test
    void indexHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }
}
