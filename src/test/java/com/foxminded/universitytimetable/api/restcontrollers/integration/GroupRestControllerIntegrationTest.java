package com.foxminded.universitytimetable.api.restcontrollers.integration;

import com.foxminded.universitytimetable.api.restcontrollers.GroupRestController;
import com.foxminded.universitytimetable.database.GroupDAO;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class GroupRestControllerIntegrationTest {
    private GroupDAO groupDAO;
    private GroupService groupService;
    private GroupRestController groupRestController;
    // For forge database connection troubles
    private GroupDAO wrongGroupDao = null;

    @BeforeEach
    void dataSet(@Autowired GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
        groupService = new GroupService(groupDAO);
        groupRestController = new GroupRestController(groupService);
    }

    @Test
    void addHaveToAddObjectToDB() {
        Group group = new Group("Test");
        groupRestController.add(group.getName());

        int expected = 1;
        int actual = groupDAO.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToReturnCorrectResult() {
        Group group = new Group("Test");
        group.setId(1);

        String expected = group.toString();
        String actual = groupRestController.add(group.getName()).toString();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> groupRestController.add(null));
        try {
            groupRestController.add(null);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.add("GroupName"));
        try {
            groupRestController.add("GroupName");
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getAllHaveToReturnCorrectResult() {
        List<Group> expectedGroups = new ArrayList<>();
        Group group = null;
        for (int i = 1; i <= 10; i++) {
            group = new Group("Test");
            group.setId(i);
            expectedGroups.add(group);
        }


        for (int i = 1; i <= 10; i++) {
            groupRestController.add(group.getName());
        }
        List<Group> actualGroups = groupRestController.getAll();


        String expected = expectedGroups.toString();
        String actual = actualGroups.toString();
        assertEquals(expected, actual);
    }
}
