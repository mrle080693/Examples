package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
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

    @Test
    void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        Group alex = new Group("alex");

        List<Group> allEmployees = Arrays.asList(alex);

        given(service.getAll()).willReturn(allEmployees);

        mvc.perform(get("/rest/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is(alex.getName())));

    }
}
