package com.foxminded.universitytimetable.daoImpl;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);
    private Group group;

    // add method
    @Test
    void addMustAddGroupToDB() {
        group = new Group("test");
        groupImpl.add(group);

        int groupsQuantity = groupImpl.getAll().size();

        assertTrue(groupsQuantity > 0);
    }

    @Test
    void addMustAddGroupWithCorrectName() {
        group = new Group("test");
        groupImpl.add(group);

        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        group = new Group("test");
        int id = groupImpl.add(group);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddGroupWithLongAndDifficultName() {
        group = new Group("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupImpl.add(group);

        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    // getAll method
    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        int expected = 0;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        group = new Group("qw");

        for (int index = 0; index < 100; index++) {
            groupImpl.add(group);
        }

        int expected = 100;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    // getById method
    @Test
    void getByIdMustReturnCorrectResult() {
        Group group = new Group("Test");
        groupImpl.add(group);

        String expected = "Test";
        String actual = groupImpl.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfTableIsNotContainsSuchId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getById(6));
    }

    // getByName method
    @Test
    void getByNameMustReturnEmptyListIfTableIsNotContainsGroupsWithSuchName() {
        int groupsQuantity = groupImpl.getByName("Test").size();
        assertTrue(groupsQuantity == 0);
    }

    @Test
    void getByNameMustReturnGroupWithInputNameIfTableContainsSuchGroupName() {
        Group group = new Group("Test");
        groupImpl.add(group);

        List<Group> groups = groupImpl.getByName("Test");

        String expected = "Test";
        String actual = groups.get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnAllGroupWithInputNameIfTableContainsSuchGroupName() {
        Group group = new Group("Test");

        for (int index = 0; index < 1000; index++) {
            groupImpl.add(group);
        }

        int expected = 1000;
        int actual = groupImpl.getByName("Test").size();

        assertEquals(expected, actual);
    }

    // update method
    @Test
    void updateMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputGroupId() {
        Group group = new Group("Test");
        group.setId(33);

        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.update(group));
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputGroupId() {
        Group group = new Group("Test");
        groupImpl.add(group);

        group.setId(1);
        group.setName("Updated");
        groupImpl.update(group);

        String expected = "Updated";
        String actual = groupImpl.getByName("Updated").get(0).getName();

        assertEquals(expected, actual);
    }

    // remove method
    @Test
    void removeMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputGroupId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.remove(1));
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputGroupId() {
        Group group = new Group("Test");
        groupImpl.add(group);
        int rowsQuantityBeforeRemove = groupImpl.getAll().size();

        groupImpl.remove(1);
        int rowsQuantityAfterRemove = groupImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}
