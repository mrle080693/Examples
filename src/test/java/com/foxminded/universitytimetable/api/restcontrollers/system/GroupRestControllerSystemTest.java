package com.foxminded.universitytimetable.api.restcontrollers.system;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.database.GroupDAO;
import com.foxminded.universitytimetable.models.Group;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupDAO groupDAO;
    private Gson gson = new Gson();

    @Test
    void addShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Group group = new Group("test");
        group.setId(1);
        String groupJson = gson.toJson(group);

        mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(groupJson));
    }

    @Test
    void addShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                .param("nam", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllShouldReturnCorrectResponse() throws Exception {
        String expected = "";
        mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON))
                .andExpect(content().string(expected));

        mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                .param("name", "test"));
        mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                .param("name", "test"));
        expected = "[{\"id\":1,\"name\":\"test\"},{\"id\":2,\"name\":\"test\"}]";
        mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON))
                .andExpect(content().string(expected));
    }

    @Test
    void getByIdShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Group group = new Group("test");
        groupDAO.add(group);
        group.setId(1);
        String groupJson = gson.toJson(group);

        mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(groupJson));
    }

    @Test
    void getByIdShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID, "wrong"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByNameShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Group group = new Group("test");
        groupDAO.add(group);
        String expected = "[{\"id\":1,\"name\":\"test\"}]";

        mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_NAME, group.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expected));
    }

    @Test
    void updateShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Group group = new Group("test");
        groupDAO.add(group);
        group.setName("updated");
        group.setId(1);
        String groupJson = gson.toJson(group);

        mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                .param("id", "1")
                .param("name", group.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(groupJson));
    }

    @Test
    void updateShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                .param("id", "1")
                .param("wrong", ""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                .param("id", "wrong")
                .param("name", "name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeShouldReturnCorrectResponseWhenRequestIsValid() throws Exception {
        Group group = new Group("Test");
        groupDAO.add(group);

        mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                .param("groupid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));
    }

    @Test
    void removeShouldReturnCorrectResponseWhenRequestIsNotValid() throws Exception {
        mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                .param("wrong", "1"))
                .andExpect(status().isBadRequest());
    }
}
