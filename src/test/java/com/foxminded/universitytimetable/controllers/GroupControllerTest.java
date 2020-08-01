package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.MvcWebConfig;
import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MvcWebConfig.class, SpringTestJdbcConfig.class})

@WebAppConfiguration
public class GroupControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void groupsUrlTest() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups"));

        mockMvc.perform(get("/groups/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void groupsSaveUrlTest() throws Exception {
        mockMvc.perform(post("/groups/save")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    public void groupsRemoveUrlTest() throws Exception {
        mockMvc.perform(post("/groups/remove")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }
}
