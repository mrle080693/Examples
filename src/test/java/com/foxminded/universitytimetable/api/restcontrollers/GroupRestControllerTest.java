package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
class GroupRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GroupService service;
    private Group group = new Group("test");

    @Test
    void addHaveToReturnOkStatusIfRequestParamIsCorrect() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rest/groups/post")
                .contentType(MediaType.APPLICATION_JSON)
                .param("group", ""))
                .andExpect(status().isOk());
    }

    @Test
    void addHaveToReturnBadRequestStatusIfRequestParamIsBad() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rest/groups/post")
                .contentType(MediaType.APPLICATION_JSON)
                .param("wrong", ""))
                .andExpect(status().is4xxClientError());
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test
    void test() throws Exception {
        group = new Group("test");
        given(service.add(group)).willReturn(group);

        List<Group> groups = new ArrayList<>();
        groups.add(group);
        given(service.getAll()).willReturn(groups);

        Gson gson = new Gson();
        String json = gson.toJson(group);

        System.out.println(json);

        mvc.perform(MockMvcRequestBuilders
                .post("/rest/groups/post")
                .param("group", json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$.group", is(json)));


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/rest/groups")
                .contentType(MediaType.APPLICATION_JSON))
                //.param("group", json))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "bebebe");
    }

    @Test
    void getAllHave()
            throws Exception {

        Group alex = new Group("alex");
        Group alex1 = new Group("alexx");

        List<Group> allEmployees = Arrays.asList(alex, alex1);

        given(service.getAll()).willReturn(allEmployees);

        mvc.perform(get("/rest/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }
}
