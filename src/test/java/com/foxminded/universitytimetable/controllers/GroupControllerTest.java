package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.MvcWebConfig;
import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
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
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
class GroupControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GroupImpl groupImpl;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void groupsHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/groups/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllHaveToReturnCorrectView() throws Exception {
        groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups"))
                .andExpect(view().name("groups"));
    }


    @Test
    void getAllHaveToReturnCorrectModel() throws Exception {
        groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().size(1));
    }

    @Test
    void getByIdHaveToReturnOkStatusIfTableGroupsHaveInputId() throws Exception {
        groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdHaveToReturnIsBadRequestStatusIfTableGroupsHaveNotInputId() throws Exception {
        mockMvc.perform(get("/groups/getById/{id}", 400))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdHaveToReturnCorrectView() throws Exception {
        groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(view().name("groups"));
    }

    @Test
    void getByIdHaveToReturnCorrectModelParameters() throws Exception {
        int groupId = groupImpl.add(new Group("Test"));

        mockMvc.perform(get("/groups/getById/{id}", groupId))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attribute("id", groupId))
                .andExpect(model().attribute("name", "Test"));
    }

    @Test
    void groupsSaveUrlTest() throws Exception {
        mockMvc.perform(post("/groups/save")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    void groupsRemoveUrlTest() throws Exception {
        mockMvc.perform(post("/groups/remove")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }
}
