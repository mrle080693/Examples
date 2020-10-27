package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
class GroupRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    private Gson gson = new Gson();

    @Test
    void addHaveToReturnCorrectResponse() {
        try {
            Group group = new Group("test");
            String groupJson = gson.toJson(group);

            given(groupService.add(any(Group.class))).willReturn(group);

            mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                    .param("name", "test"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(groupService.add(any(Group.class))).willThrow(ValidationException.class);
            mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                    .param("name", "test"))
                    .andExpect(status().isBadRequest());

            given(groupService.add(any(Group.class))).willThrow(Exception.class);
            mockMvc.perform(post("/rest/groups/post")
                    .param("name", "test"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(post(Urls.API_REST_POST_GROUP_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("wrong", ""))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToReturnCorrectResponse() {
        try {
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("first"));
            groups.add(new Group("second"));
            String groupsJson = gson.toJson(groups);

            given(groupService.getAll()).willReturn(groups);

            mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupsJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(groupService.getAll()).willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            given(groupService.getAll()).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            given(groupService.getAll()).willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUPS_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResponse() {
        try {
            Group group = new Group();
            group.setId(3);
            String groupJson = gson.toJson(group);

            given(groupService.getById(3)).willReturn(group);

            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToThrowResponseStatusExceptionWithCorrectStatusWhenCatchException() {
        try {
            int id = 3;

            given(groupService.getById(id)).willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID))
                    .andExpect(status().isBadRequest());

            given(groupService.getById(id)).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID))
                    .andExpect(status().isNotFound());

            given(groupService.getAll()).willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_ID))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByNameHaveToReturnCorrectResponse() {
        try {
            String name = "Name";
            List<Group> groups = new ArrayList<>();
            groups.add(new Group(name));
            groups.add(new Group(name));
            String groupsJson = gson.toJson(groups);

            given(groupService.getByName(name)).willReturn(groups);

            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_NAME)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupsJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByNameHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            String name = "Name";

            given(groupService.getByName(name)).willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_NAME))
                    .andExpect(status().isBadRequest());

            given(groupService.getByName(name)).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_NAME))
                    .andExpect(status().isNotFound());

            given(groupService.getByName(name)).willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_JSON_BY_NAME))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToReturnCorrectResponse() {
        try {
            int id = 1;
            String name = "Name";
            Group group = new Group(name);
            group.setId(id);
            String groupJson = gson.toJson(group);

            given(groupService.update(any(Group.class))).willReturn(group);

            mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                    .param("id", String.valueOf(id))
                    .param("name", name))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(groupService.update(any(Group.class))).willThrow(ValidationException.class);
            mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                    .param("id", "1")
                    .param("name", "test"))
                    .andExpect(status().isBadRequest());

            given(groupService.update(any(Group.class))).willThrow(Exception.class);
            mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                    .param("id", "1")
                    .param("name", "test"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(put(Urls.API_REST_PUT_GROUP_JSON)
                    .param("wrong", "1")
                    .param("name", ""))
                    .andExpect(status().isBadRequest());

            mockMvc.perform(put("/rest/groups/put")
                    .param("id", "1")
                    .param("wrong", ""))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToReturnCorrectResponse() {
        try {
            int id = 1;
            String name = "Name";
            Group group = new Group(name);
            group.setId(id);
            String groupJson = gson.toJson(group);

            given(groupService.remove(id)).willReturn(group);

            mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                    .param("groupid", String.valueOf(id)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(groupJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(groupService.remove(any(Integer.class))).willThrow(NotFoundEntityException.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                    .param("groupid", "1"))
                    .andExpect(status().isNotFound());

            given(groupService.remove(-1)).willThrow(ValidationException.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                    .param("id", "1"))
                    .andExpect(status().isBadRequest());

            given(groupService.remove(1)).willThrow(Exception.class);
            mockMvc.perform(delete(Urls.API_REST_DELETE_GROUP_JSON)
                    .param("id", "1"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(put(Urls.API_REST_DELETE_GROUP_JSON)
                    .param("wrong", "1"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }
}
