package com.foxminded.universitytimetable.daoImpl;

import com.foxminded.universitytimetable.configurations.SpringContextConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupImplTest {
    private AnnotationConfigApplicationContext context = SpringContextConfig.context;
    private GroupImpl groupImpl;
    private Group group;
    private EmbeddedDatabase db;

    @BeforeEach
    void deleteAllFromTables() {
        db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("test.sql")
                .build();
        groupImpl = context.getBean("groupImplBean", GroupImpl.class);
        groupImpl.setDataSource(db);
    }

    // add method
    @Test
    void addMustThrowNullPointerExceptionIfInputIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> groupImpl.add(null));
    }

    @Test
    void addMustThrowNullPointerExceptionIfGroupNameIsNull() {
        group = new Group(null);
        Assertions.assertThrows(NullPointerException.class, () -> groupImpl.add(group));
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfGroupNameIsEmptyString() {
        group = new Group("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.add(group));
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfGroupNameIsOnlySeparators() {
        group = new Group("   ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.add(group));
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfGroupIdIsNotZero() {
        group = new Group("Test");
        group.setId(2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.add(group));
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
    void addMustAddGroupWithLongAndDifficultName() {
        group = new Group("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupImpl.add(group);

        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    // getAll method
    // Have to return NotFoundEntityException
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
    void getByIdMustReturnNotFoundEntityExceptionIfInputIsZero() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getById(0));
    }

    @Test
    void getByIdMustReturnNotFoundEntityExceptionIfInputIsWrongId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getById(-789987));
    }

    @Test
    void getByIdMustReturnCorrectResult() {
        Group group = new Group("Test");
        groupImpl.add(group);

        String expected = "Test";
        String actual = groupImpl.getById(1).getName();

        assertEquals(expected, actual);
    }

    // getByName method
    @Test
    void getByNameMustThrowNullPointerExceptionIfInputIsNull(){
        Assertions.assertThrows(NullPointerException.class, () -> groupImpl.getByName(null));
    }

    // Have to return NotFoundEntityException
    @Test
    void getByNameMustThrowNotFoundEntityExceptionIfTableNotExistGroupWithSuchName(){
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getByName("HJK"));
    }
}
