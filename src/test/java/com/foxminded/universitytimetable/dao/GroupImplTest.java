package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.ProfessorImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupImplTest {
    private DataSource dataSource;
    private static AnnotationConfigApplicationContext context;

    private static GroupImpl groupImpl;
    private static Group group;

    @BeforeAll
    static void initialize() {
        context = new AnnotationConfigApplicationContext(SpringTestJdbcConfig.class);
        groupImpl = context.getBean("groupImplBean", GroupImpl.class);
    }

    @BeforeEach
    void dataSet() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test.sql")
                .build();

        group = new Group("test");
        groupImpl.add(group);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = groupImpl.add(group);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddGroupWithCorrectName() {
        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddGroupWithLongAndDifficultName() {
        group.setName("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupImpl.add(group);

        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        groupImpl.remove(1);

        int expected = 0;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            groupImpl.add(group);
        }

        int expected = 100;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnGroupWithCorrectName() {
        String expected = "test";
        String actual = groupImpl.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnEmptyListIfTableIsNotContainsGroupsWithSuchName() {
        groupImpl.remove(1);

        int groupsQuantity = groupImpl.getByName("Test").size();
        assertTrue(groupsQuantity == 0);
    }

    @Test
    void getByNameMustReturnGroupWithInputNameIfTableContainsSuchGroupName() {
        List<Group> groups = groupImpl.getByName("test");

        String expected = "test";
        String actual = groups.get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnAllGroupWithInputNameIfTableContainsSuchGroupName() {
        for (int index = 1; index < 1000; index++) {
            groupImpl.add(group);
        }

        int expected = 1000;
        int actual = groupImpl.getByName("test").size();

        assertEquals(expected, actual);
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputGroupId() {
        group.setId(1);
        group.setName("Updated");
        groupImpl.update(group);

        String expected = "Updated";
        String actual = groupImpl.getByName("Updated").get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputGroupId() {
        int rowsQuantityBeforeRemove = groupImpl.getAll().size();

        groupImpl.remove(1);
        int rowsQuantityAfterRemove = groupImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }

    @Test
    void removeMustCascadeRemoveLessonsWithItsGroup() {
        Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1,
                1, "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Math");
        LessonImpl lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
        ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);

        professorImpl.add(professor);
        lessonImpl.add(lesson);

        int lessonsQuantityBeforeRemove = lessonImpl.getAll().size();

        groupImpl.remove(1);

        int lessonsQuantityAfterRemove = lessonImpl.getAll().size();

        assertTrue(lessonsQuantityBeforeRemove > lessonsQuantityAfterRemove);
    }
}
