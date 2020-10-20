package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.database.impl.GroupImpl;
import com.foxminded.universitytimetable.services.exceptions.DAOException;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GroupServiceTest {
    private GroupImpl groupImpl;
    @Autowired
    private GroupService groupService;
    private Group group;

    @BeforeEach
    void dataSet() {
        group = new Group("Test");
        groupImpl = Mockito.mock(GroupImpl.class);
        //groupService = new GroupService(groupImpl);
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputGroupIdIsNotZero() {
        group.setId(7);
        Assertions.assertThrows(ValidationException.class, () -> groupService.add(group));
    }

    @Test
    void addMustThrowDAOExceptionIfGroupImplThrowsDataAccessException() {
        when(groupImpl.add(group)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> groupService.add(group));
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfInputGroupIsNull() {
        group = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.add(group));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfGroupNameIsNull() {
        group.setName(null);
        Assertions.assertThrows(ValidationException.class, () -> groupService.add(group));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfGroupNameIsEmpty() {
        group.setName("");
        Assertions.assertThrows(ValidationException.class, () -> groupService.add(group));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfGroupNameIsSeparators() {
        group.setName("   ");
        Assertions.assertThrows(ValidationException.class, () -> groupService.add(group));
    }

    @Test
    void addMustReturnTheSameThatGroupImplWasReturn() {
        when(groupImpl.add(group)).thenReturn(1);

        int expected = 1;
        Group actual = groupService.add(group);

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustThrowsDAOExceptionIfGroupImplThrowsDataAccessException() {
        when(groupImpl.getAll()).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> groupService.getAll());
    }

    @Test
    void getAllMustThrowNotFoundEntityExceptionIfGroupImplReturnEmptyList() {
        List<Group> expected = new ArrayList<>();

        when(groupImpl.getAll()).thenReturn(expected);

        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.getAll());
    }

    @Test
    void getAllMustReturnTheSameThatGroupImplWasReturn() {
        List<Group> expected = new ArrayList<>();
        expected.add(group);

        when(groupImpl.getAll()).thenReturn(expected);

        List<Group> actual = groupService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowEntityValidationExceptionIfInputGroupIdIsZero() {
        Assertions.assertThrows(ValidationException.class, () -> groupService.getById(0));
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfGroupImplThrowEmptyResultDataAccessException() {
        when(groupImpl.getById(1)).thenThrow(new EmptyResultDataAccessException(1));

        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.getById(1));
    }

    @Test
    void getByIdMustThrowsDAOExceptionIfGroupImplThrowsDataAccessException() {
        when(groupImpl.getById(1)).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> groupService.getById(1));
    }

    @Test
    void getByIdMustReturnTheSameThatGroupImplWasReturn() {
        Group expected = group;

        when(groupImpl.getById(1)).thenReturn(expected);

        Group actual = groupService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustThrowEntityValidationExceptionIfInputGroupNameIsNull() {
        String groupName = null;
        Assertions.assertThrows(ValidationException.class, () -> groupService.getByName(groupName));
    }

    @Test
    void getByNameMustThrowEntityValidationExceptionIfInputGroupNameIsEmpty() {
        String groupName = "";
        Assertions.assertThrows(ValidationException.class, () -> groupService.getByName(groupName));
    }

    @Test
    void getByNameMustThrowEntityValidationExceptionIfInputGroupNameIsSeparators() {
        String groupName = "     ";
        Assertions.assertThrows(ValidationException.class, () -> groupService.getByName(groupName));
    }

    @Test
    void getByNameMustThrowNotFoundEntityExceptionIfGroupImplThrowEmptyResultDataAccessException() {
        when(groupImpl.getByName("Test")).thenThrow(new EmptyResultDataAccessException(1));

        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.getByName("Test"));
    }

    @Test
    void getByNameMustThrowsDAOExceptionIfGroupImplThrowsDataAccessException() {
        when(groupImpl.getByName("Test")).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> groupService.getByName("Test"));
    }

    @Test
    void getByNameMustReturnTheSameThatGroupImplWasReturn() {
        List<Group> expected = new ArrayList<>();
        expected.add(group);

        when(groupImpl.getByName("Test")).thenReturn(expected);

        List<Group> actual = groupService.getByName("Test");

        assertEquals(expected, actual);
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputGroupIdIsZero() {
        Assertions.assertThrows(ValidationException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowDAOExceptionIfGroupImplThrowsDataAccessException() {
        group.setId(1);

        when(groupImpl.update(group)).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowIllegalArgumentExceptionIfInputGroupIsNull() {
        group = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfGroupNameIsNull() {
        group.setName(null);
        Assertions.assertThrows(ValidationException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfGroupNameIsEmpty() {
        group.setName("");
        Assertions.assertThrows(ValidationException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfGroupNameIsSeparators() {
        group.setName("   ");
        Assertions.assertThrows(ValidationException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustThrowNotFoundEntityExceptionIfGroupImplReturnStatusNotOne() {
        group.setId(8);

        when(groupImpl.update(group)).thenReturn(-1);

        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.update(group));
    }

    @Test
    void updateMustReturnTheSameThatGroupImplWasReturn() {
        group.setId(1);

        when(groupImpl.update(group)).thenReturn(1);

        int expected = 1;
        Group actual = groupService.update(group);

        assertEquals(expected, actual);
    }

    @Test
    void removeMustThrowNotFoundEntityExceptionIfGroupImplReturnStatusNotOne() {
        when(groupImpl.remove(7)).thenReturn(-1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.remove(1));
    }

    @Test
    void removeMustThrowDAOExceptionIfGroupImplThrowsDataAccessException() {
        when(groupImpl.remove(7)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> groupService.remove(7));
    }

    @Test
    void removeMustReturnStatusOneIfGroupImplWasReturnStatusOne() {
        int expected = 1;
        when(groupImpl.remove(1)).thenReturn(expected);

        int actual = groupService.remove(1);

        assertEquals(expected, actual);
    }
}
