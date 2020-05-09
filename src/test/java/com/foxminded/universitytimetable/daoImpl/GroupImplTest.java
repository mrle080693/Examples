package com.foxminded.universitytimetable.daoImpl;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);
    private Group group;

    @BeforeEach
    void deleteAllFromTables() {
    //    db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("test.sql")
      //          .build();
    }

    // add method
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

    // getByName method


    @Test
    void getByNameMustThrowNotFoundEntityExceptionIfTableNotExistGroupWithSuchName(){
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getByName("HJK"));
    }
}
