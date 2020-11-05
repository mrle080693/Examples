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
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    @Test
    void getAllHaveToThrowCorrectException() {
        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.getAll());
        try {
            groupRestController.getAll();
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResult() {
        Group group = new Group("Test");
        groupDAO.add(group);

        group.setId(1);
        String expected = group.toString();
        String actual = groupRestController.getById(1).toString();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> groupRestController.getById(-12));
        try {
            groupRestController.getById(-12);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.getById(1));
        try {
            groupRestController.getById(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getByNameHaveToReturnCorrectResult() {
        Group group = new Group("Test");
        groupDAO.add(group);

        Group expectedGroup = new Group("Test");
        expectedGroup.setId(1);
        String expected = expectedGroup.toString();
        String actual = groupRestController.getByName(group.getName()).get(0).toString();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> groupRestController.getByName(null));
        try {
            groupRestController.getByName(null);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.getByName("Random"));
        try {
            groupRestController.getByName("Random");
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void updateHaveToUpdateObjectInDB() {
        groupRestController.add("oldName");
        groupRestController.update(1, "newName");

        String expected = "newName";
        String actual = groupDAO.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToReturnCorrectResult() {
        Group group = new Group("oldName");
        groupRestController.add(group.getName());

        String expected = "newName";
        String actual = groupRestController.update(1, "newName").getName();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> groupRestController.update(1, null));
        try {
            groupRestController.update(1, null);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.update(1, "GroupName"));
        try {
            groupRestController.update(1, "GroupName");
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void removeHaveToDeleteObjectFromDB() {
        Group group = new Group("Test");
        groupDAO.add(group);
        // Check if group was added
        int expected = 1;
        int actual = groupDAO.getAll().size();
        assertEquals(expected, actual);

        // Check group was deleted
        groupRestController.remove(1);
        expected = 0;
        actual = groupDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToReturnCorrectResult() {
        Group group = new Group("Test");
        groupDAO.add(group);
        group.setId(1);
        int expected = 1;
        int actual = groupRestController.remove(1);

        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> groupRestController.remove(-9));
        try {
            groupRestController.remove(-5);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        groupService = new GroupService(wrongGroupDao);
        groupRestController = new GroupRestController(groupService);
        assertThrows(Exception.class, () -> groupRestController.remove(1));
        try {
            groupRestController.remove(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }
}
