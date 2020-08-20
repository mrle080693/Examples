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
@ContextConfiguration(classes = {MvcWebConfig.class, SpringTestJdbcConfig.class})
@WebAppConfiguration
// Some tests may be single running
class GroupControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addHaveToReturnRedirectToGroups() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    void addHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("wrong", "updatedGroup"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void groupsHaveToReturnIsNotFoundStatusIfUrlIsWrong() throws Exception {
        mockMvc.perform(get("/groups/ss"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getGroupsViewAndAllGroupsHaveToReturnOkStatusIfUrlIsCorrect() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk());
    }

    @Test
    void getGroupsViewAndAllGroupsHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(view().name("groups"));
    }

    @Test
    void getGroupsViewAndAllGroupsHaveToReturnCorrectModel() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().size(1));
    }

    // Single running test
    @Test
    void getByIdHaveToReturnOkStatusIfTableGroupsHaveInputId() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(get("/groups/getById/{id}", 1)
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdHaveToReturnIsBadRequestStatusIfTableGroupsHaveNotInputId() throws Exception {
        mockMvc.perform(get("/groups/getById/{id}", 400))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdHaveToReturnCorrectView() throws Exception {
        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(view().name("groups"));
    }

    @Test
    void getByIdHaveToReturnCorrectModelParameters() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "1")
                .param("newName", "Test"));

        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attribute("id", 1));
    }

    @Test
    void updateHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/groups/update")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateHaveToReturnRedirectToGroups() throws Exception {
        mockMvc.perform(post("/groups/update")
                .param("id", "1")
                .param("newName", "updatedGroup"))
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    void updateHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/groups/update")
                .param("wrong", "updatedGroup"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateHaveToReturnRedirectionStatusIfUpdatedGroupDoesNotExist() throws Exception {
        mockMvc.perform(post("/groups/update")
                .param("id", "190000000")
                .param("newName", "updatedGroup"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectionStatus() throws Exception {
        mockMvc.perform(post("/groups/remove")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void removeHaveToReturnRedirectToGroups() throws Exception {
        mockMvc.perform(post("/groups/remove")
                .param("id", "1"))
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    void removeHaveToReturnClientErrorIfRequestNotContainsParameterWhichControllerNeeds() throws Exception {
        mockMvc.perform(post("/groups/remove")
                .param("wrong", "updatedGroup"))
                .andExpect(status().is4xxClientError());
    }

    // Single running test
    @Test
    void removeHaveToRemove() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "0")
                .param("newName", "updatedGroup"));

        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/groups/remove")
                .param("id", "1")
                .param("newName", "updatedGroup"));

        mockMvc.perform(get("/groups/getById/{id}", 1))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void removeHaveToReturnRedirectionStatusIfRemovedGroupDoesNotExist() throws Exception {
        mockMvc.perform(post("/groups/add")
                .param("id", "0")
                .param("newName", "updatedGroup"));

        mockMvc.perform(post("/groups/remove")
                .param("id", "190000000")
                .param("newNam", "updatedGroup"))
                .andExpect(status().is3xxRedirection());
    }
}
